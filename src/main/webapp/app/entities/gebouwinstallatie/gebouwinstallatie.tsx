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

import { getEntities } from './gebouwinstallatie.reducer';

export const Gebouwinstallatie = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const gebouwinstallatieList = useAppSelector(state => state.gebouwinstallatie.entities);
  const loading = useAppSelector(state => state.gebouwinstallatie.loading);

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
      <h2 id="gebouwinstallatie-heading" data-cy="GebouwinstallatieHeading">
        Gebouwinstallaties
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/gebouwinstallatie/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Gebouwinstallatie
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {gebouwinstallatieList && gebouwinstallatieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidgebouwinstallatie')}>
                  Datumbegingeldigheidgebouwinstallatie{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidgebouwinstallatie')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidgebouwinstallatie')}>
                  Datumeindegeldigheidgebouwinstallatie{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidgebouwinstallatie')} />
                </th>
                <th className="hand" onClick={sort('geometriegebouwinstallatie')}>
                  Geometriegebouwinstallatie <FontAwesomeIcon icon={getSortIconByFieldName('geometriegebouwinstallatie')} />
                </th>
                <th className="hand" onClick={sort('identificatiegebouwinstallatie')}>
                  Identificatiegebouwinstallatie <FontAwesomeIcon icon={getSortIconByFieldName('identificatiegebouwinstallatie')} />
                </th>
                <th className="hand" onClick={sort('lod0geometriegebouwinstallatie')}>
                  Lod 0 Geometriegebouwinstallatie <FontAwesomeIcon icon={getSortIconByFieldName('lod0geometriegebouwinstallatie')} />
                </th>
                <th className="hand" onClick={sort('relatievehoogteligginggebouwinstallatie')}>
                  Relatievehoogteligginggebouwinstallatie{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('relatievehoogteligginggebouwinstallatie')} />
                </th>
                <th className="hand" onClick={sort('statusgebouwinstallatie')}>
                  Statusgebouwinstallatie <FontAwesomeIcon icon={getSortIconByFieldName('statusgebouwinstallatie')} />
                </th>
                <th className="hand" onClick={sort('typegebouwinstallatie')}>
                  Typegebouwinstallatie <FontAwesomeIcon icon={getSortIconByFieldName('typegebouwinstallatie')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {gebouwinstallatieList.map((gebouwinstallatie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/gebouwinstallatie/${gebouwinstallatie.id}`} color="link" size="sm">
                      {gebouwinstallatie.id}
                    </Button>
                  </td>
                  <td>
                    {gebouwinstallatie.datumbegingeldigheidgebouwinstallatie ? (
                      <TextFormat
                        type="date"
                        value={gebouwinstallatie.datumbegingeldigheidgebouwinstallatie}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>
                    {gebouwinstallatie.datumeindegeldigheidgebouwinstallatie ? (
                      <TextFormat
                        type="date"
                        value={gebouwinstallatie.datumeindegeldigheidgebouwinstallatie}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>{gebouwinstallatie.geometriegebouwinstallatie}</td>
                  <td>{gebouwinstallatie.identificatiegebouwinstallatie}</td>
                  <td>{gebouwinstallatie.lod0geometriegebouwinstallatie}</td>
                  <td>{gebouwinstallatie.relatievehoogteligginggebouwinstallatie}</td>
                  <td>{gebouwinstallatie.statusgebouwinstallatie}</td>
                  <td>{gebouwinstallatie.typegebouwinstallatie}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/gebouwinstallatie/${gebouwinstallatie.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/gebouwinstallatie/${gebouwinstallatie.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/gebouwinstallatie/${gebouwinstallatie.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Gebouwinstallaties found</div>
        )}
      </div>
    </div>
  );
};

export default Gebouwinstallatie;
