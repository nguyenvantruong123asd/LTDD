import {CCard, CCardBody, CCardFooter, CCardHeader} from "@coreui/react";
import {yupResolver} from "@hookform/resolvers/yup";
import {makeStyles} from "@material-ui/core";
import classNames from "classnames";
import {useSnackbar} from "notistack";
import React, {useState} from "react";
import {Controller, useForm} from "react-hook-form";
import courseApi from "src/api/courseApi";
import CustomButton from "src/components/CustomButton";
import CustomDialog from "src/components/CustomDialog";
import CustomInput from "src/components/CustomInput";
import CustomRichEditor from "src/components/CustomRichEditor";
import CustomSelectForm from "src/components/form/CustomSelectForm";
import {convertVND, isEmpty} from "src/Tool/Tools";
import * as yup from "yup";
import FCategorie from "../../categorie/FCategorie";
import SimpleDialog from "./SimpleDialog";

const useStyles = makeStyles((theme) => ({
    form: {
        "& > div": {
            display: "flex",
            alignItems: "center",
            "& > span": {
                minWidth: "150px",
                fontSize: " 16px",
            },
            marginBottom: "30px",
        },
        "& > div:last-of-type": {
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
        },
    },
    price: {},
    footer: {
        display: "flex",
        justifyContent: "space-between",
    },
    visiable: {
        visibility: "hidden",
    },
    center: {
        display: "flex",
        justifyContent: "space-evenly",
        alignItems: "center",
    },
    posterImage: {
        width: "25%",
        cursor: "pointer",
        // marginInline: "auto",
    },
    posterImageDialog: {
        width: "100%",
        margin: "20px 0px 40px 0px",
    },
    [theme.breakpoints.down("md")]: {
        form: {
            "& > div": {
                display: "flex",
                flexFlow: "column",
                alignItems: "start",
                "& > span": {
                    marginBottom: "15px",
                },
            },
            "& > div:last-of-type": {},
        },
        posterImage: {
            width: "100%",
            height: "auto",
            cursor: "pointer",
        },
        posterImageDialog: {
            width: "100%",
            margin: "20px 0px 40px 0px",
        },
        center: {
            display: "flex",
            flexFlow: "column",
            justifyContent: "center",
            alignItems: "center",
            marginInline: "auto",
            "&>img": {
                marginBottom: "20px",
            },
        },
    },
}));
const schema = yup.object().shape({
    title: yup.string().required("Vui l??ng nh???p ti??u ?????"),
    price: yup
        .string()
        .required("Vui l??ng nh???p gi?? ti???n")
        .test("check number price", "Vui l??ng nh???p ????ng ki???u gi?? ti???n", (price) => {
            const regularExp = /^\d+$/;
            if (price.match(regularExp)) {
                return true;
            } else {
                return false;
            }
        }),
    discount: yup
        .string()
        .required("Vui l??ng nh???p ????ng % gi???m gi??")
        .test(
            "check number discount",
            "Vui l??ng nh???p ????ng ki???u gi?? ti???n",
            (discount) => {
                const regularExp = /^\d+$/;
                if (discount.match(regularExp)) {
                    if (discount <= 100) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        ),
});

function FBaseCourse(props) {
    const classes = useStyles();
    const {
        nextCurrentStep = null,
        step = 0,
        currentStep = 0,
        prevStep = null,
        nextStep = null,
        dataCourse = {},
        updateCourse = null,
    } = props;
    const {enqueueSnackbar} = useSnackbar();
    const [dataSelect, setDataSelect] = useState([]);
    //dialog
    const [isOpenDialogCategory, setIsOpenDialogCategory] = React.useState(false);
    const [richText, setRichText] = React.useState(
        dataCourse?.longDescription || ""
    );
    console.log(richText);
    React.useEffect(() => {
        getCategories();

        return () => {
            setDataSelect([]);
        };
    }, []);
    const form = useForm({
        mode: "onBlur",
        defaultValues: {
            title: dataCourse?.title || "",
            longDescription: dataCourse?.longDescription || "",
            price: dataCourse?.originPrice || "",
            discount: dataCourse?.discount || 0,
            description: dataCourse?.description || "",
            longDescription: dataCourse?.longDescription || "",
            categoryId: dataCourse?.categoryId || "",
        },
        resolver: yupResolver(schema),
    });
    const handleOnSubmit = (values) => {
        // console.log(values);
        // console.log(richText);
        if (isEmpty(values.categoryId)) {
            enqueueSnackbar("Vui l??ng ch???n th??? lo???i", {variant: "error"});
            return;
        }
        if (isEmpty(values?.imageThumbnail?.name)) {
            enqueueSnackbar("Vui t???i h??nh l??n", {variant: "error"});
            return;
        }

        (async () => {
            console.log("POST NEW COURSE", values);
            const formData = new FormData();
            formData.append("title", values.title);
            formData.append("imageThumbnail", values.imageThumbnail);
            formData.append("categoryId", values.categoryId);
            formData.append("price", values.price);
            formData.append("discount", values.discount);
            formData.append("description", values.description);
            formData.append("longDescription", richText);
            try {
                const rp = await courseApi.postNewCourse(formData);
                console.log(rp);
                if (!rp.status) {
                    console.log(rp);
                    if (nextCurrentStep) nextCurrentStep(1);
                    if (updateCourse) updateCourse(rp);
                    enqueueSnackbar("T???o kh??a h???c th??nh c??ng", {variant: "success"});
                } else {
                    enqueueSnackbar(rp?.data?.message, {variant: "error"});
                }
            } catch (error) {
                enqueueSnackbar(error.message, {variant: "error"});
            }
        })();
    };

    const getCategories = async () => {
        const res = await courseApi.categories();
        setDataSelect(
            Array.from(res).map((item, index) => ({
                value: item.id,
                title: item.name,
            }))
        );
    };
    const [img, setImg] = useState(dataCourse?.imagePoster?.image);
    const [open, setOpen] = useState(false);

    const handleClickOpen = () => {
        setOpen(!open);
    };
    const contentSimpleDialog = (
        <img src={img} className={classes.posterImageDialog} alt=""/>
    );
    const handleChangeImg = () => {
        const inputFile = document.getElementById("input-thumbnail");
        inputFile.click();
    };
    const handleOnChangeFile = (event) => {
        const file = event.target.files[0];
        // console.log(file);
        const tmpImg = URL.createObjectURL(file);
        setImg(tmpImg);
        form.setValue("imageThumbnail", file);
    };
    return (
        <CCard>
            <CCardHeader>
                <span className="title">Th??ng tin c?? b???n</span>
            </CCardHeader>
            <CCardBody>
                <form
                    className={classes.form}
                    onSubmit={form.handleSubmit(handleOnSubmit)}
                >
                    <div>
                        <span>T??n kh??a h???c</span>
                        <CustomInput
                            name="title"
                            title="T??n kh??a h???c"
                            label="T??n kh??a h???c"
                            form={form}
                        />
                    </div>
                    <div>
                        <span>Gi??</span>
                        <CustomInput
                            name="price"
                            title="Gi?? kh??a h???c"
                            label="Gi?? kh??a h???c"
                            form={form}
                        />
                    </div>
                    <div>
                        <span>Gi???m gi?? 0-100%</span>
                        <CustomInput
                            name="discount"
                            title="Gi???m gi??"
                            label="Gi???m gi??"
                            form={form}
                        />
                    </div>
                    <div className={classes.price}>
                        <span>Hi???n th??? gi?? ti???n</span>
                        <span>
              {convertVND(
                  form.watch("price") -
                  (form.watch("price") * form.watch("discount")) / 100
              )}
            </span>
                    </div>
                    <div>
                        <span>M?? t??? ng???n</span>
                        <CustomInput
                            name="description"
                            title="M?? t??? kh??a h???c"
                            label="M?? t??? kh??a h???c"
                            multiline={true}
                            rows={4}
                            form={form}
                        />
                    </div>
                    <div>
                        <span>M?? t??? d??i</span>
                        <div>
                            <CustomRichEditor
                                name="longDescription"
                                value={richText}
                                onChange={(value) => {
                                    setRichText(value);
                                }}
                            />
                        </div>
                    </div>
                    <div className="FBaseCourse-item__category">
                        <span>Ch???n th??? lo???i</span>
                        <CustomSelectForm
                            name={"categoryId"}
                            label="Ch???n th??? lo???i"
                            data={dataSelect}
                            form={form}
                        />
                        <CustomButton
                            title="Th??m th??? lo???i"
                            onClick={() => {
                                setIsOpenDialogCategory(true);
                            }}
                        />
                    </div>

                    <div>
                        <span>H??nh m?? t???</span>
                        <div className={classes.center}>
                            <img
                                src={img}
                                className={classNames(!isEmpty(img) && classes.posterImage)}
                                onClick={handleClickOpen}
                                alt=""
                            />
                            <SimpleDialog
                                open={open}
                                id={`form-create-course-poster`}
                                onClose={handleClickOpen}
                                title="Image Poster"
                                content={contentSimpleDialog}
                            />
                            <input
                                accept="image/*"
                                type="file"
                                name="img-thumbnail"
                                id="input-thumbnail"
                                onChange={handleOnChangeFile}
                                hidden
                            />
                            <CustomButton
                                title="Upload"
                                color="secondary"
                                variant="contained"
                                onClick={handleChangeImg}
                                css={false}
                            />
                        </div>
                    </div>

                    <div>
                        <CustomButton type="submit" title="G???i"/>
                    </div>
                </form>
            </CCardBody>
            <CCardFooter className={classes.footer}>
                <div className={classNames(step <= 0 && classes.visiable)}>
                    <CustomButton
                        title="Tr?????c"
                        onClick={() => {
                            if (prevStep) prevStep();
                        }}
                    />
                </div>
                <div className={classNames(step >= currentStep && classes.visiable)}>
                    <CustomButton
                        title="Sau"
                        onClick={() => {
                            if (nextStep) nextStep();
                        }}
                    />
                </div>
            </CCardFooter>
            <CustomDialog
                closeDialog={() => {
                    setIsOpenDialogCategory(false);
                }}
                isOpen={isOpenDialogCategory}
                id="category"
                title=""
                content={
                    <FCategorie
                        onUpdate={() => {
                            getCategories();
                        }}
                    />
                }
            />
        </CCard>
    );
}

export default FBaseCourse;
