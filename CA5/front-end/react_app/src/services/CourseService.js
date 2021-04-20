import {COURSES_URL} from "../config/config";

const axios = require("axios").default;

export default class CourseService {

    static async getCourses() {
        let courses = await axios.get(COURSES_URL);
        console.log(courses);
        return courses.data;
    }
}