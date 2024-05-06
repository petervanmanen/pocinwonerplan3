import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './trajectactiviteit.reducer';

export const Trajectactiviteit = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const trajectactiviteitList = useAppSelector(state => state.trajectactiviteit.entities);
  const loading = useAppSelector(state => state.trajectactiviteit.loading);

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
      <h2 id="trajectactiviteit-heading" data-cy="TrajectactiviteitHeading">
        Trajectactiviteits
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/trajectactiviteit/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Trajectactiviteit
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {trajectactiviteitList && trajectactiviteitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('contract')}>
                  Contract <FontAwesomeIcon icon={getSortIconByFieldName('contract')} />
                </th>
                <th className="hand" onClick={sort('crediteur')}>
                  Crediteur <FontAwesomeIcon icon={getSortIconByFieldName('crediteur')} />
                </th>
                <th className="hand" onClick={sort('datumbegin')}>
                  Datumbegin <FontAwesomeIcon icon={getSortIconByFieldName('datumbegin')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumstatus')}>
                  Datumstatus <FontAwesomeIcon icon={getSortIconByFieldName('datumstatus')} />
                </th>
                <th className="hand" onClick={sort('kinderopvang')}>
                  Kinderopvang <FontAwesomeIcon icon={getSortIconByFieldName('kinderopvang')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('succesvol')}>
                  Succesvol <FontAwesomeIcon icon={getSortIconByFieldName('succesvol')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {trajectactiviteitList.map((trajectactiviteit, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/trajectactiviteit/${trajectactiviteit.id}`} color="link" size="sm">
                      {trajectactiviteit.id}
                    </Button>
                  </td>
                  <td>{trajectactiviteit.contract}</td>
                  <td>{trajectactiviteit.crediteur}</td>
                  <td>
                    {trajectactiviteit.datumbegin ? (
                      <TextFormat type="date" value={trajectactiviteit.datumbegin} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {trajectactiviteit.datumeinde ? (
                      <TextFormat type="date" value={trajectactiviteit.datumeinde} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {trajectactiviteit.datumstatus ? (
                      <TextFormat type="date" value={trajectactiviteit.datumstatus} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{trajectactiviteit.kinderopvang}</td>
                  <td>{trajectactiviteit.status}</td>
                  <td>{trajectactiviteit.succesvol}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/trajectactiviteit/${trajectactiviteit.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/trajectactiviteit/${trajectactiviteit.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/trajectactiviteit/${trajectactiviteit.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Trajectactiviteits found</div>
        )}
      </div>
    </div>
  );
};

export default Trajectactiviteit;
