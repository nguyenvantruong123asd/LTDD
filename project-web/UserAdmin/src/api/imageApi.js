import axiosClient from "./axiosClient";

const imageApi = {
    getAll() {
        const url = `/api/admin/image/list`;
        return axiosClient.get(url);
    },
    getImageByTagName(tagname) {
        const url = `/api/admin/image/tagname?tagname=${tagname}`;
        return axiosClient.get(url);
    },
    add(data) {
        //image,name,description
        const url = `/api/admin/image`;
        return axiosClient.post(url, data);
    },
    delete(id) {
        const url = `/api/admin/image/${id}`;
        return axiosClient.delete(url);
    },
};

export default imageApi;
