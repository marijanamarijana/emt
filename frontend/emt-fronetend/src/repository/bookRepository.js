import axiosInstance from "../axios/axios.js";

const bookRepository = {
    findAll: async () => {
        return await axiosInstance.get("/books");
    },
    findById: async (id) => {
        return await axiosInstance.get(`/books/${id}`);
    },
    add: async (data) => {
        return await axiosInstance.post("/books/add", data);
    },
    edit: async (id, data) => {
        return await axiosInstance.put(`/books/edit/${id}`, data);
    },
    delete: async (id) => {
        return await axiosInstance.delete(`/books/delete/${id}`);
    },

};
export default bookRepository;
