export const capitalize = (value) => {

    if (!value) {
        return "";
    }


    return value.charAt(0).toUpperCase() + value.slice(1);

};




export const truncate = (value, length = 50) => {

    if (!value) {
        return "";
    }


    if (value.length <= length) {
        return value;
    }


    return `${value.substring(0, length)}...`;

};




export const isEmpty = (value) => {

    if (value === null || value === undefined) {
        return true;
    }


    if (typeof value === "string") {
        return value.trim().length === 0;
    }


    if (Array.isArray(value)) {
        return value.length === 0;
    }


    if (typeof value === "object") {
        return Object.keys(value).length === 0;
    }


    return false;

};




export const generateId = () => {

    return Date.now() + Math.floor(
        Math.random() * 1000
    );

};




export const removeDuplicates = (array = []) => {

    return [...new Set(array)];

};




export const sortByDate = (
    items = [],
    key = "date"
) => {

    return [...items].sort(
        (a, b) =>
            new Date(b[key]) - new Date(a[key])
    );

};