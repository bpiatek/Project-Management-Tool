import React, {Component} from 'react';
import Link from "react-router-dom/es/Link";

class MainPage extends Component {
    render() {
        return (
            <div className="container">
                <div className="jumbotron text-center">
                    <h1 className="display-4">Welcome to the app!</h1>
                    <p className="lead">
                        This is a Jira like project management app created especially for programming workshop
                        on UAM.
                    </p>
                    <p>by Bartosz Piątek</p>
                    <hr className="my-4"/>
                        <p>
                            To go to the projects click on the button below.
                        </p>
                        <p className="lead">
                            <Link to="/dashboard" className="btn btn-primary btn-lg">
                                Projects dashboard
                            </Link>
                        </p>
                </div>
            </div>
        );
    }
}

export default MainPage;
