import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './organisatorischeeenheid.reducer';

export const Organisatorischeeenheid = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const organisatorischeeenheidList = useAppSelector(state => state.organisatorischeeenheid.entities);
  const loading = useAppSelector(state => state.organisatorischeeenheid.loading);

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
      <h2 id="organisatorischeeenheid-heading" data-cy="OrganisatorischeeenheidHeading">
        Organisatorischeeenheids
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/organisatorischeeenheid/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Organisatorischeeenheid
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {organisatorischeeenheidList && organisatorischeeenheidList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumontstaan')}>
                  Datumontstaan <FontAwesomeIcon icon={getSortIconByFieldName('datumontstaan')} />
                </th>
                <th className="hand" onClick={sort('datumopheffing')}>
                  Datumopheffing <FontAwesomeIcon icon={getSortIconByFieldName('datumopheffing')} />
                </th>
                <th className="hand" onClick={sort('emailadres')}>
                  Emailadres <FontAwesomeIcon icon={getSortIconByFieldName('emailadres')} />
                </th>
                <th className="hand" onClick={sort('faxnummer')}>
                  Faxnummer <FontAwesomeIcon icon={getSortIconByFieldName('faxnummer')} />
                </th>
                <th className="hand" onClick={sort('formatie')}>
                  Formatie <FontAwesomeIcon icon={getSortIconByFieldName('formatie')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('naamverkort')}>
                  Naamverkort <FontAwesomeIcon icon={getSortIconByFieldName('naamverkort')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('organisatieidentificatie')}>
                  Organisatieidentificatie <FontAwesomeIcon icon={getSortIconByFieldName('organisatieidentificatie')} />
                </th>
                <th className="hand" onClick={sort('telefoonnummer')}>
                  Telefoonnummer <FontAwesomeIcon icon={getSortIconByFieldName('telefoonnummer')} />
                </th>
                <th className="hand" onClick={sort('toelichting')}>
                  Toelichting <FontAwesomeIcon icon={getSortIconByFieldName('toelichting')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {organisatorischeeenheidList.map((organisatorischeeenheid, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/organisatorischeeenheid/${organisatorischeeenheid.id}`} color="link" size="sm">
                      {organisatorischeeenheid.id}
                    </Button>
                  </td>
                  <td>{organisatorischeeenheid.datumontstaan}</td>
                  <td>{organisatorischeeenheid.datumopheffing}</td>
                  <td>{organisatorischeeenheid.emailadres}</td>
                  <td>{organisatorischeeenheid.faxnummer}</td>
                  <td>{organisatorischeeenheid.formatie}</td>
                  <td>{organisatorischeeenheid.naam}</td>
                  <td>{organisatorischeeenheid.naamverkort}</td>
                  <td>{organisatorischeeenheid.omschrijving}</td>
                  <td>{organisatorischeeenheid.organisatieidentificatie}</td>
                  <td>{organisatorischeeenheid.telefoonnummer}</td>
                  <td>{organisatorischeeenheid.toelichting}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/organisatorischeeenheid/${organisatorischeeenheid.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/organisatorischeeenheid/${organisatorischeeenheid.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/organisatorischeeenheid/${organisatorischeeenheid.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Organisatorischeeenheids found</div>
        )}
      </div>
    </div>
  );
};

export default Organisatorischeeenheid;
