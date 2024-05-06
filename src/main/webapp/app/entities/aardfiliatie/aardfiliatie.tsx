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

import { getEntities } from './aardfiliatie.reducer';

export const Aardfiliatie = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const aardfiliatieList = useAppSelector(state => state.aardfiliatie.entities);
  const loading = useAppSelector(state => state.aardfiliatie.loading);

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
      <h2 id="aardfiliatie-heading" data-cy="AardfiliatieHeading">
        Aardfiliaties
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/aardfiliatie/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Aardfiliatie
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {aardfiliatieList && aardfiliatieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('codeaardfiliatie')}>
                  Codeaardfiliatie <FontAwesomeIcon icon={getSortIconByFieldName('codeaardfiliatie')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidaardfiliatie')}>
                  Datumbegingeldigheidaardfiliatie <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidaardfiliatie')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidaardfiliatie')}>
                  Datumeindegeldigheidaardfiliatie <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidaardfiliatie')} />
                </th>
                <th className="hand" onClick={sort('naamaardfiliatie')}>
                  Naamaardfiliatie <FontAwesomeIcon icon={getSortIconByFieldName('naamaardfiliatie')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {aardfiliatieList.map((aardfiliatie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/aardfiliatie/${aardfiliatie.id}`} color="link" size="sm">
                      {aardfiliatie.id}
                    </Button>
                  </td>
                  <td>{aardfiliatie.codeaardfiliatie}</td>
                  <td>
                    {aardfiliatie.datumbegingeldigheidaardfiliatie ? (
                      <TextFormat type="date" value={aardfiliatie.datumbegingeldigheidaardfiliatie} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {aardfiliatie.datumeindegeldigheidaardfiliatie ? (
                      <TextFormat type="date" value={aardfiliatie.datumeindegeldigheidaardfiliatie} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{aardfiliatie.naamaardfiliatie}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/aardfiliatie/${aardfiliatie.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/aardfiliatie/${aardfiliatie.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/aardfiliatie/${aardfiliatie.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Aardfiliaties found</div>
        )}
      </div>
    </div>
  );
};

export default Aardfiliatie;
