import axios from "axios";
import {DELETE_PROJECT_TASK, GET_BACKLOG, GET_ERRORS, GET_PROJECT_TASK} from "./types";

export const addProjectTask = (id, projectTask, history) => async dispatch => {
    try {
        await axios.post(`/api/backlog/${id}`, projectTask);
        history.push(`/projectBoard/${id}`);
        dispatch({
                     type: GET_ERRORS,
                     payload: {}
                 });
    } catch (e) {
        dispatch({
                     type: GET_ERRORS,
                     payload: e.response.data
                 });
    }
};

export const getBacklog = (id) => async dispatch => {
    try {
        const res = await axios.get(`/api/backlog/${id}`);
        dispatch({
                     type: GET_BACKLOG,
                     payload: res.data
                 });
    } catch (e) {
        dispatch({
                     type: GET_ERRORS,
                     payload: e.response.data
                 });
    }
};

export const getProjectTask = (backlogId, projectTaskId, history) => async dispatch => {
    try {
        const res = await axios.get(`/api/backlog/${backlogId}/${projectTaskId}`);
        dispatch({
                     type: GET_PROJECT_TASK,
                     payload: res.data
                 });
    } catch (e) {
        history.push("/dashboard");
    }
};

export const updateProjectTask = (backlogId, projectTaskId, projectTask, history) => async dispatch => {
    try {
        await axios.patch(`/api/backlog/${backlogId}/${projectTaskId}`, projectTask);
        history.push(`/projectBoard/${backlogId}`);
        dispatch({
                     type: GET_ERRORS,
                     payload: {}
                 });
    } catch (e) {
        dispatch({
                     type: GET_ERRORS,
                     payload: e.response.data
                 });
    }
};

export const deleteProjectTask = (backlogId, projectTaskId) => async dispatch => {
    if (window.confirm(`Dou you really want to delete this project task? ${projectTaskId}`)) {
        await axios.delete(`/api/backlog/${backlogId}/${projectTaskId}`);
        dispatch({
                     type: DELETE_PROJECT_TASK,
                     payload: projectTaskId
                 });
    }

};
