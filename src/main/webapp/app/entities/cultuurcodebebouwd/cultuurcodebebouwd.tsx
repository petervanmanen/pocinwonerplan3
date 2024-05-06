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

import { getEntities } from './cultuurcodebebouwd.reducer';

export const Cultuurcodebebouwd = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const cultuurcodebebouwdList = useAppSelector(state => state.cultuurcodebebouwd.entities);
  const loading = useAppSelector(state => state.cultuurcodebebouwd.loading);

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
      <h2 id="cultuurcodebebouwd-heading" data-cy="CultuurcodebebouwdHeading">
        Cultuurcodebebouwds
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/cultuurcodebebouwd/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Cultuurcodebebouwd
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {cultuurcodebebouwdList && cultuurcodebebouwdList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('cultuurcodebebouwd')}>
                  Cultuurcodebebouwd <FontAwesomeIcon icon={getSortIconByFieldName('cultuurcodebebouwd')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidcultuurcodebebouwd')}>
                  Datumbegingeldigheidcultuurcodebebouwd{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidcultuurcodebebouwd')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidcultuurcodebebouwd')}>
                  Datumeindegeldigheidcultuurcodebebouwd{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidcultuurcodebebouwd')} />
                </th>
                <th className="hand" onClick={sort('naamcultuurcodebebouwd')}>
                  Naamcultuurcodebebouwd <FontAwesomeIcon icon={getSortIconByFieldName('naamcultuurcodebebouwd')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {cultuurcodebebouwdList.map((cultuurcodebebouwd, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/cultuurcodebebouwd/${cultuurcodebebouwd.id}`} color="link" size="sm">
                      {cultuurcodebebouwd.id}
                    </Button>
                  </td>
                  <td>{cultuurcodebebouwd.cultuurcodebebouwd}</td>
                  <td>
                    {cultuurcodebebouwd.datumbegingeldigheidcultuurcodebebouwd ? (
                      <TextFormat
                        type="date"
                        value={cultuurcodebebouwd.datumbegingeldigheidcultuurcodebebouwd}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>
                    {cultuurcodebebouwd.datumeindegeldigheidcultuurcodebebouwd ? (
                      <TextFormat
                        type="date"
                        value={cultuurcodebebouwd.datumeindegeldigheidcultuurcodebebouwd}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>{cultuurcodebebouwd.naamcultuurcodebebouwd}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/cultuurcodebebouwd/${cultuurcodebebouwd.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/cultuurcodebebouwd/${cultuurcodebebouwd.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/cultuurcodebebouwd/${cultuurcodebebouwd.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Cultuurcodebebouwds found</div>
        )}
      </div>
    </div>
  );
};

export default Cultuurcodebebouwd;
