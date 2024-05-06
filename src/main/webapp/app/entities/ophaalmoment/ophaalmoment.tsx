import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './ophaalmoment.reducer';

export const Ophaalmoment = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const ophaalmomentList = useAppSelector(state => state.ophaalmoment.entities);
  const loading = useAppSelector(state => state.ophaalmoment.loading);

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
      <h2 id="ophaalmoment-heading" data-cy="OphaalmomentHeading">
        Ophaalmoments
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/ophaalmoment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Ophaalmoment
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ophaalmomentList && ophaalmomentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('gewichtstoename')}>
                  Gewichtstoename <FontAwesomeIcon icon={getSortIconByFieldName('gewichtstoename')} />
                </th>
                <th className="hand" onClick={sort('tijdstip')}>
                  Tijdstip <FontAwesomeIcon icon={getSortIconByFieldName('tijdstip')} />
                </th>
                <th>
                  Gelost Container <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Gestoptop Locatie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Rit <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ophaalmomentList.map((ophaalmoment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/ophaalmoment/${ophaalmoment.id}`} color="link" size="sm">
                      {ophaalmoment.id}
                    </Button>
                  </td>
                  <td>{ophaalmoment.gewichtstoename}</td>
                  <td>{ophaalmoment.tijdstip}</td>
                  <td>
                    {ophaalmoment.gelostContainer ? (
                      <Link to={`/container/${ophaalmoment.gelostContainer.id}`}>{ophaalmoment.gelostContainer.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {ophaalmoment.gestoptopLocatie ? (
                      <Link to={`/locatie/${ophaalmoment.gestoptopLocatie.id}`}>{ophaalmoment.gestoptopLocatie.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{ophaalmoment.heeftRit ? <Link to={`/rit/${ophaalmoment.heeftRit.id}`}>{ophaalmoment.heeftRit.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/ophaalmoment/${ophaalmoment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/ophaalmoment/${ophaalmoment.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/ophaalmoment/${ophaalmoment.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Ophaalmoments found</div>
        )}
      </div>
    </div>
  );
};

export default Ophaalmoment;
