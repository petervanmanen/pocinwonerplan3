import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './inkomensvoorzieningsoort.reducer';

export const Inkomensvoorzieningsoort = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const inkomensvoorzieningsoortList = useAppSelector(state => state.inkomensvoorzieningsoort.entities);
  const loading = useAppSelector(state => state.inkomensvoorzieningsoort.loading);

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
      <h2 id="inkomensvoorzieningsoort-heading" data-cy="InkomensvoorzieningsoortHeading">
        Inkomensvoorzieningsoorts
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/inkomensvoorzieningsoort/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Inkomensvoorzieningsoort
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {inkomensvoorzieningsoortList && inkomensvoorzieningsoortList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  Code <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('regeling')}>
                  Regeling <FontAwesomeIcon icon={getSortIconByFieldName('regeling')} />
                </th>
                <th className="hand" onClick={sort('regelingcode')}>
                  Regelingcode <FontAwesomeIcon icon={getSortIconByFieldName('regelingcode')} />
                </th>
                <th className="hand" onClick={sort('vergoeding')}>
                  Vergoeding <FontAwesomeIcon icon={getSortIconByFieldName('vergoeding')} />
                </th>
                <th className="hand" onClick={sort('vergoedingscode')}>
                  Vergoedingscode <FontAwesomeIcon icon={getSortIconByFieldName('vergoedingscode')} />
                </th>
                <th className="hand" onClick={sort('wet')}>
                  Wet <FontAwesomeIcon icon={getSortIconByFieldName('wet')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {inkomensvoorzieningsoortList.map((inkomensvoorzieningsoort, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/inkomensvoorzieningsoort/${inkomensvoorzieningsoort.id}`} color="link" size="sm">
                      {inkomensvoorzieningsoort.id}
                    </Button>
                  </td>
                  <td>{inkomensvoorzieningsoort.code}</td>
                  <td>{inkomensvoorzieningsoort.naam}</td>
                  <td>{inkomensvoorzieningsoort.omschrijving}</td>
                  <td>{inkomensvoorzieningsoort.regeling}</td>
                  <td>{inkomensvoorzieningsoort.regelingcode}</td>
                  <td>{inkomensvoorzieningsoort.vergoeding}</td>
                  <td>{inkomensvoorzieningsoort.vergoedingscode}</td>
                  <td>{inkomensvoorzieningsoort.wet}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/inkomensvoorzieningsoort/${inkomensvoorzieningsoort.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/inkomensvoorzieningsoort/${inkomensvoorzieningsoort.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/inkomensvoorzieningsoort/${inkomensvoorzieningsoort.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Inkomensvoorzieningsoorts found</div>
        )}
      </div>
    </div>
  );
};

export default Inkomensvoorzieningsoort;
