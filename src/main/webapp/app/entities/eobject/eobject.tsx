import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './eobject.reducer';

export const Eobject = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const eobjectList = useAppSelector(state => state.eobject.entities);
  const loading = useAppSelector(state => state.eobject.loading);

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
      <h2 id="eobject-heading" data-cy="EobjectHeading">
        Eobjects
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/eobject/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Eobject
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {eobjectList && eobjectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('adresbinnenland')}>
                  Adresbinnenland <FontAwesomeIcon icon={getSortIconByFieldName('adresbinnenland')} />
                </th>
                <th className="hand" onClick={sort('adresbuitenland')}>
                  Adresbuitenland <FontAwesomeIcon icon={getSortIconByFieldName('adresbuitenland')} />
                </th>
                <th className="hand" onClick={sort('domein')}>
                  Domein <FontAwesomeIcon icon={getSortIconByFieldName('domein')} />
                </th>
                <th className="hand" onClick={sort('geometrie')}>
                  Geometrie <FontAwesomeIcon icon={getSortIconByFieldName('geometrie')} />
                </th>
                <th className="hand" onClick={sort('identificatie')}>
                  Identificatie <FontAwesomeIcon icon={getSortIconByFieldName('identificatie')} />
                </th>
                <th className="hand" onClick={sort('indicatierisico')}>
                  Indicatierisico <FontAwesomeIcon icon={getSortIconByFieldName('indicatierisico')} />
                </th>
                <th className="hand" onClick={sort('kadastraleaanduiding')}>
                  Kadastraleaanduiding <FontAwesomeIcon icon={getSortIconByFieldName('kadastraleaanduiding')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('eobjecttype')}>
                  Eobjecttype <FontAwesomeIcon icon={getSortIconByFieldName('eobjecttype')} />
                </th>
                <th className="hand" onClick={sort('toelichting')}>
                  Toelichting <FontAwesomeIcon icon={getSortIconByFieldName('toelichting')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {eobjectList.map((eobject, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/eobject/${eobject.id}`} color="link" size="sm">
                      {eobject.id}
                    </Button>
                  </td>
                  <td>{eobject.adresbinnenland}</td>
                  <td>{eobject.adresbuitenland}</td>
                  <td>{eobject.domein}</td>
                  <td>{eobject.geometrie}</td>
                  <td>{eobject.identificatie}</td>
                  <td>{eobject.indicatierisico}</td>
                  <td>{eobject.kadastraleaanduiding}</td>
                  <td>{eobject.naam}</td>
                  <td>{eobject.eobjecttype}</td>
                  <td>{eobject.toelichting}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/eobject/${eobject.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/eobject/${eobject.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/eobject/${eobject.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Eobjects found</div>
        )}
      </div>
    </div>
  );
};

export default Eobject;
