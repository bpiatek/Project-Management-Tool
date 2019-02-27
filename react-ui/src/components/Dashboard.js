import React, {Component} from 'react';
import ProjectItem from "./project/ProjectItem";
import CreateProjectButton from "./project/CreateProjectButton";
import {getProjects} from "../actions/projectActions";
import {connect} from "react-redux";
import PropTypes from "prop-types";

class Dashboard extends Component {

    componentDidMount() {
        this.props.getProjects();
    }

    render() {
        const {projects} = this.props.project;

        return (
            <div className="projects">
                <div className="container">
                    <div className="row">
                        <div className="col-md-12">
                            <h1 className="display-4 text-center">Projects</h1>
                            <br/>
                            <CreateProjectButton/>
                            <br/>
                            <hr/>
                            { projects.map(project => (
                                <ProjectItem key={project.id} project = {project}/>
                                ))
                            }
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = state => ({
    project: state.project
});

Dashboard.propTypes = {
    project: PropTypes.object.isRequired,
    getProjects: PropTypes.func.isRequired
};

export default connect(mapStateToProps, {getProjects})(Dashboard);