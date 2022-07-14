import {CCard, CCardBody, CCardHeader} from "@coreui/react";
import {yupResolver} from "@hookform/resolvers/yup";
import {Grid} from "@material-ui/core";
import {useSnackbar} from "notistack";
import React from "react";
import {useForm} from "react-hook-form";
import categoriesApi from "src/api/categoriesApi";
import notificationApi from "src/api/notificationApi";
import CustomButton from "src/components/CustomButton";
import CustomInput from "src/components/CustomInput";
import * as yup from "yup";

const scheme = yup.object().shape({
    subject: yup.string().required("Vui lòng nhập tiêu đề"),
    content: yup.string().required("Vui lòng nhập nội dung thông báo"),
});

const FNotification = () => {
    const {enqueueSnackbar} = useSnackbar();
    const form = useForm({
        mode: "onBlur",
        defaultValues: {
            subject: "",
            content: "",
        },
        resolver: yupResolver(scheme),
    });

    const handleOnSubmit = async (values) => {
        try {
            const res = await notificationApi.new(values);
            console.log(res);
            if (!!!res.status) {
                enqueueSnackbar("Tạo thể loại thành công", {variant: "success"});
                form.reset();
            }
        } catch (error) {
            enqueueSnackbar(error.message, {variant: "error"});
        }
    };
    return (
        <>
            <Grid container className="form-notification" spacing={3}>
                <Grid item xl={12} lg={12} md={12} xs={12} sm={12}>
                    <CCard>
                        <CCardHeader>
                            <span className="title">Form tạo mới thông báo</span>
                        </CCardHeader>
                        <CCardBody>
                            <form
                                className="form"
                                onSubmit={form.handleSubmit(handleOnSubmit)}
                            >
                                <div className="itemForm">
                                    <span>Tiêu đề *</span>
                                    <CustomInput
                                        name="subject"
                                        title="Tiêu đề"
                                        label="Tiêu đề"
                                        form={form}
                                    />
                                </div>
                                <div className="itemForm">
                                    <span>Nội dung thông báo *</span>
                                    <CustomInput
                                        name="content"
                                        title="Nội dung thông báo"
                                        label="Nội dung thông báo"
                                        form={form}
                                    />
                                </div>

                                <div className="lastItemForm">
                                    <CustomButton type="submit" title="Gửi"/>
                                </div>
                            </form>
                        </CCardBody>
                        {/* <CCardFooter></CCardFooter> */}
                    </CCard>
                </Grid>
            </Grid>
        </>
    );
};

export default FNotification;
