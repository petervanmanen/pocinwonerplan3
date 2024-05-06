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

import { getEntities } from './cultuurcodeonbebouwd.reducer';

export const Cultuurcodeonbebouwd = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const cultuurcodeonbebouwdList = useAppSelector(state => state.cultuurcodeonbebouwd.entities);
  const loading = useAppSelector(state => state.cultuurcodeonbebouwd.loading);

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
      <h2 id="cultuurcodeonbebouwd-heading" data-cy="CultuurcodeonbebouwdHeading">
        Cultuurcodeonbebouwds
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/cultuurcodeonbebouwd/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Cultuurcodeonbebouwd
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {cultuurcodeonbebouwdList && cultuurcodeonbebouwdList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('cultuurcodeonbebouwd')}>
                  Cultuurcodeonbebouwd <FontAwesomeIcon icon={getSortIconByFieldName('cultuurcodeonbebouwd')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidcultuurcodeonbebouwd')}>
                  Datumbegingeldigheidcultuurcodeonbebouwd{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidcultuurcodeonbebouwd')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidcultuurcodeonbebouwd')}>
                  Datumeindegeldigheidcultuurcodeonbebouwd{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidcultuurcodeonbebouwd')} />
                </th>
                <th className="hand" onClick={sort('naamcultuurcodeonbebouwd')}>
                  Naamcultuurcodeonbebouwd <FontAwesomeIcon icon={getSortIconByFieldName('naamcultuurcodeonbebouwd')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {cultuurcodeonbebouwdList.map((cultuurcodeonbebouwd, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/cultuurcodeonbebouwd/${cultuurcodeonbebouwd.id}`} color="link" size="sm">
                      {cultuurcodeonbebouwd.id}
                    </Button>
                  </td>
                  <td>{cultuurcodeonbebouwd.cultuurcodeonbebouwd}</td>
                  <td>
                    {cultuurcodeonbebouwd.datumbegingeldigheidcultuurcodeonbebouwd ? (
                      <TextFormat
                        type="date"
                        value={cultuurcodeonbebouwd.datumbegingeldigheidcultuurcodeonbebouwd}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>
                    {cultuurcodeonbebouwd.datumeindegeldigheidcultuurcodeonbebouwd ? (
                      <TextFormat
                        type="date"
                        value={cultuurcodeonbebouwd.datumeindegeldigheidcultuurcodeonbebouwd}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>{cultuurcodeonbebouwd.naamcultuurcodeonbebouwd}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/cultuurcodeonbebouwd/${cultuurcodeonbebouwd.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/cultuurcodeonbebouwd/${cultuurcodeonbebouwd.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/cultuurcodeonbebouwd/${cultuurcodeonbebouwd.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Cultuurcodeonbebouwds found</div>
        )}
      </div>
    </div>
  );
};

export default Cultuurcodeonbebouwd;
