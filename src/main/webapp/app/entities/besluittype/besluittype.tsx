import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './besluittype.reducer';

export const Besluittype = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const besluittypeList = useAppSelector(state => state.besluittype.entities);
  const loading = useAppSelector(state => state.besluittype.loading);

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
      <h2 id="besluittype-heading" data-cy="BesluittypeHeading">
        Besluittypes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/besluittype/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Besluittype
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {besluittypeList && besluittypeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('besluitcategorie')}>
                  Besluitcategorie <FontAwesomeIcon icon={getSortIconByFieldName('besluitcategorie')} />
                </th>
                <th className="hand" onClick={sort('besluittypeomschrijving')}>
                  Besluittypeomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('besluittypeomschrijving')} />
                </th>
                <th className="hand" onClick={sort('besluittypeomschrijvinggeneriek')}>
                  Besluittypeomschrijvinggeneriek <FontAwesomeIcon icon={getSortIconByFieldName('besluittypeomschrijvinggeneriek')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidbesluittype')}>
                  Datumbegingeldigheidbesluittype <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidbesluittype')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidbesluittype')}>
                  Datumeindegeldigheidbesluittype <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidbesluittype')} />
                </th>
                <th className="hand" onClick={sort('indicatiepublicatie')}>
                  Indicatiepublicatie <FontAwesomeIcon icon={getSortIconByFieldName('indicatiepublicatie')} />
                </th>
                <th className="hand" onClick={sort('publicatietekst')}>
                  Publicatietekst <FontAwesomeIcon icon={getSortIconByFieldName('publicatietekst')} />
                </th>
                <th className="hand" onClick={sort('publicatietermijn')}>
                  Publicatietermijn <FontAwesomeIcon icon={getSortIconByFieldName('publicatietermijn')} />
                </th>
                <th className="hand" onClick={sort('reactietermijn')}>
                  Reactietermijn <FontAwesomeIcon icon={getSortIconByFieldName('reactietermijn')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {besluittypeList.map((besluittype, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/besluittype/${besluittype.id}`} color="link" size="sm">
                      {besluittype.id}
                    </Button>
                  </td>
                  <td>{besluittype.besluitcategorie}</td>
                  <td>{besluittype.besluittypeomschrijving}</td>
                  <td>{besluittype.besluittypeomschrijvinggeneriek}</td>
                  <td>{besluittype.datumbegingeldigheidbesluittype}</td>
                  <td>{besluittype.datumeindegeldigheidbesluittype}</td>
                  <td>{besluittype.indicatiepublicatie}</td>
                  <td>{besluittype.publicatietekst}</td>
                  <td>{besluittype.publicatietermijn}</td>
                  <td>{besluittype.reactietermijn}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/besluittype/${besluittype.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/besluittype/${besluittype.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/besluittype/${besluittype.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Besluittypes found</div>
        )}
      </div>
    </div>
  );
};

export default Besluittype;
