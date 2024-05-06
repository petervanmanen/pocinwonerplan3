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

import { getEntities } from './aardaantekening.reducer';

export const Aardaantekening = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const aardaantekeningList = useAppSelector(state => state.aardaantekening.entities);
  const loading = useAppSelector(state => state.aardaantekening.loading);

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
      <h2 id="aardaantekening-heading" data-cy="AardaantekeningHeading">
        Aardaantekenings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/aardaantekening/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Aardaantekening
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {aardaantekeningList && aardaantekeningList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('codeaardaantekening')}>
                  Codeaardaantekening <FontAwesomeIcon icon={getSortIconByFieldName('codeaardaantekening')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidaardaantekening')}>
                  Datumbegingeldigheidaardaantekening{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidaardaantekening')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidaardaantekening')}>
                  Datumeindegeldigheidaardaantekening{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidaardaantekening')} />
                </th>
                <th className="hand" onClick={sort('naamaardaantekening')}>
                  Naamaardaantekening <FontAwesomeIcon icon={getSortIconByFieldName('naamaardaantekening')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {aardaantekeningList.map((aardaantekening, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/aardaantekening/${aardaantekening.id}`} color="link" size="sm">
                      {aardaantekening.id}
                    </Button>
                  </td>
                  <td>{aardaantekening.codeaardaantekening}</td>
                  <td>
                    {aardaantekening.datumbegingeldigheidaardaantekening ? (
                      <TextFormat type="date" value={aardaantekening.datumbegingeldigheidaardaantekening} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {aardaantekening.datumeindegeldigheidaardaantekening ? (
                      <TextFormat type="date" value={aardaantekening.datumeindegeldigheidaardaantekening} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{aardaantekening.naamaardaantekening}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/aardaantekening/${aardaantekening.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/aardaantekening/${aardaantekening.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/aardaantekening/${aardaantekening.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Aardaantekenings found</div>
        )}
      </div>
    </div>
  );
};

export default Aardaantekening;
