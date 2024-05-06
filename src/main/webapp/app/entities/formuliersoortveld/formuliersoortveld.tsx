import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './formuliersoortveld.reducer';

export const Formuliersoortveld = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const formuliersoortveldList = useAppSelector(state => state.formuliersoortveld.entities);
  const loading = useAppSelector(state => state.formuliersoortveld.loading);

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
      <h2 id="formuliersoortveld-heading" data-cy="FormuliersoortveldHeading">
        Formuliersoortvelds
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/formuliersoortveld/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Formuliersoortveld
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {formuliersoortveldList && formuliersoortveldList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('helptekst')}>
                  Helptekst <FontAwesomeIcon icon={getSortIconByFieldName('helptekst')} />
                </th>
                <th className="hand" onClick={sort('isverplicht')}>
                  Isverplicht <FontAwesomeIcon icon={getSortIconByFieldName('isverplicht')} />
                </th>
                <th className="hand" onClick={sort('label')}>
                  Label <FontAwesomeIcon icon={getSortIconByFieldName('label')} />
                </th>
                <th className="hand" onClick={sort('maxlengte')}>
                  Maxlengte <FontAwesomeIcon icon={getSortIconByFieldName('maxlengte')} />
                </th>
                <th className="hand" onClick={sort('veldnaam')}>
                  Veldnaam <FontAwesomeIcon icon={getSortIconByFieldName('veldnaam')} />
                </th>
                <th className="hand" onClick={sort('veldtype')}>
                  Veldtype <FontAwesomeIcon icon={getSortIconByFieldName('veldtype')} />
                </th>
                <th>
                  Heeftvelden Formuliersoort <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {formuliersoortveldList.map((formuliersoortveld, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/formuliersoortveld/${formuliersoortveld.id}`} color="link" size="sm">
                      {formuliersoortveld.id}
                    </Button>
                  </td>
                  <td>{formuliersoortveld.helptekst}</td>
                  <td>{formuliersoortveld.isverplicht ? 'true' : 'false'}</td>
                  <td>{formuliersoortveld.label}</td>
                  <td>{formuliersoortveld.maxlengte}</td>
                  <td>{formuliersoortveld.veldnaam}</td>
                  <td>{formuliersoortveld.veldtype}</td>
                  <td>
                    {formuliersoortveld.heeftveldenFormuliersoort ? (
                      <Link to={`/formuliersoort/${formuliersoortveld.heeftveldenFormuliersoort.id}`}>
                        {formuliersoortveld.heeftveldenFormuliersoort.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/formuliersoortveld/${formuliersoortveld.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/formuliersoortveld/${formuliersoortveld.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/formuliersoortveld/${formuliersoortveld.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Formuliersoortvelds found</div>
        )}
      </div>
    </div>
  );
};

export default Formuliersoortveld;
