import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './clientbegeleider.reducer';

export const Clientbegeleider = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const clientbegeleiderList = useAppSelector(state => state.clientbegeleider.entities);
  const loading = useAppSelector(state => state.clientbegeleider.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="clientbegeleider-heading" data-cy="ClientbegeleiderHeading">
        Clientbegeleiders
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/clientbegeleider/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Clientbegeleider
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {clientbegeleiderList && clientbegeleiderList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('begeleiderscode')}>
                  Begeleiderscode <FontAwesomeIcon icon={getSortIconByFieldName('begeleiderscode')} />
                </th>
                <th>
                  Maaktonderdeeluitvan Team <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Begeleidt Traject <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Ondersteuntclient Client <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {clientbegeleiderList.map((clientbegeleider, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/clientbegeleider/${clientbegeleider.id}`} color="link" size="sm">
                      {clientbegeleider.id}
                    </Button>
                  </td>
                  <td>{clientbegeleider.begeleiderscode}</td>
                  <td>
                    {clientbegeleider.maaktonderdeeluitvanTeam ? (
                      <Link to={`/team/${clientbegeleider.maaktonderdeeluitvanTeam.id}`}>
                        {clientbegeleider.maaktonderdeeluitvanTeam.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {clientbegeleider.begeleidtTraject ? (
                      <Link to={`/traject/${clientbegeleider.begeleidtTraject.id}`}>{clientbegeleider.begeleidtTraject.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {clientbegeleider.ondersteuntclientClients
                      ? clientbegeleider.ondersteuntclientClients.map((val, j) => (
                          <span key={j}>
                            <Link to={`/client/${val.id}`}>{val.id}</Link>
                            {j === clientbegeleider.ondersteuntclientClients.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/clientbegeleider/${clientbegeleider.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/clientbegeleider/${clientbegeleider.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/clientbegeleider/${clientbegeleider.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Clientbegeleiders found</div>
        )}
      </div>
    </div>
  );
};

export default Clientbegeleider;
