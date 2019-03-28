import React, {Component} from 'react';
import Link from "react-router-dom/es/Link";

class Header extends Component {
    render() {
        return (
            <div>
                <nav className="navbar navbar-expand-sm navbar-dark bg-primary mb-4">
                    <div className="container">
                        <Link to={`/`} className="navbar-brand">
                            JIRA Like Tool by Bartosz Piątek
                        </Link>

                        <button className="navbar-toggler" type="button" data-toggle="collapse"
                                data-target="#mobile-nav">
                            <span className="navbar-toggler-icon"/>
                        </button>

                        <div className="collapse navbar-collapse" id="mobile-nav">
                            <ul className="navbar-nav mr-auto">
                                <li className="nav-item">
                                    <Link to={`/dashboard`} className="nav-link">
                                        Dashboard
                                    </Link>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>
            </div>
        );
    }
}

export default Header;
