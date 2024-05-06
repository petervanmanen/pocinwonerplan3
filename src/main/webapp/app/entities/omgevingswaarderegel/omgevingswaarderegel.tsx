import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './omgevingswaarderegel.reducer';

export const Omgevingswaarderegel = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const omgevingswaarderegelList = useAppSelector(state => state.omgevingswaarderegel.entities);
  const loading = useAppSelector(state => state.omgevingswaarderegel.loading);

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
      <h2 id="omgevingswaarderegel-heading" data-cy="OmgevingswaarderegelHeading">
        Omgevingswaarderegels
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/omgevingswaarderegel/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Omgevingswaarderegel
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {omgevingswaarderegelList && omgevingswaarderegelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('groep')}>
                  Groep <FontAwesomeIcon icon={getSortIconByFieldName('groep')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th>
                  Beschrijft Omgevingsnorm <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Beschrijft Omgevingswaarde <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {omgevingswaarderegelList.map((omgevingswaarderegel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/omgevingswaarderegel/${omgevingswaarderegel.id}`} color="link" size="sm">
                      {omgevingswaarderegel.id}
                    </Button>
                  </td>
                  <td>{omgevingswaarderegel.groep}</td>
                  <td>{omgevingswaarderegel.naam}</td>
                  <td>
                    {omgevingswaarderegel.beschrijftOmgevingsnorms
                      ? omgevingswaarderegel.beschrijftOmgevingsnorms.map((val, j) => (
                          <span key={j}>
                            <Link to={`/omgevingsnorm/${val.id}`}>{val.id}</Link>
                            {j === omgevingswaarderegel.beschrijftOmgevingsnorms.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {omgevingswaarderegel.beschrijftOmgevingswaardes
                      ? omgevingswaarderegel.beschrijftOmgevingswaardes.map((val, j) => (
                          <span key={j}>
                            <Link to={`/omgevingswaarde/${val.id}`}>{val.id}</Link>
                            {j === omgevingswaarderegel.beschrijftOmgevingswaardes.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/omgevingswaarderegel/${omgevingswaarderegel.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/omgevingswaarderegel/${omgevingswaarderegel.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/omgevingswaarderegel/${omgevingswaarderegel.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Omgevingswaarderegels found</div>
        )}
      </div>
    </div>
  );
};

export default Omgevingswaarderegel;
