import axiosInstance from "../axios/axios.js";

const authorRepository = {
    findAll: async () => {
        return await axiosInstance.get("/authors");
    },
    findById: async (id) => {
        return await axiosInstance.get(`/authors/${id}`);
    },
    add: async (data) => {
        return await axiosInstance.post("/authors/add", data);
    },
    edit: async (id, data) => {
        return await axiosInstance.put(`/authors/edit/${id}`, data);
    },
    delete: async (id) => {
        return await axiosInstance.delete(`/authors/delete/${id}`);
    },

};
export default authorRepository;
