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

import { getEntities } from './locatieonroerendezaak.reducer';

export const Locatieonroerendezaak = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const locatieonroerendezaakList = useAppSelector(state => state.locatieonroerendezaak.entities);
  const loading = useAppSelector(state => state.locatieonroerendezaak.loading);

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
      <h2 id="locatieonroerendezaak-heading" data-cy="LocatieonroerendezaakHeading">
        Locatieonroerendezaaks
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/locatieonroerendezaak/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Locatieonroerendezaak
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {locatieonroerendezaakList && locatieonroerendezaakList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('adrestype')}>
                  Adrestype <FontAwesomeIcon icon={getSortIconByFieldName('adrestype')} />
                </th>
                <th className="hand" onClick={sort('cultuurcodebebouwd')}>
                  Cultuurcodebebouwd <FontAwesomeIcon icon={getSortIconByFieldName('cultuurcodebebouwd')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheid')}>
                  Datumbegingeldigheid <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheid')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheid')}>
                  Datumeindegeldigheid <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheid')} />
                </th>
                <th className="hand" onClick={sort('geometrie')}>
                  Geometrie <FontAwesomeIcon icon={getSortIconByFieldName('geometrie')} />
                </th>
                <th className="hand" onClick={sort('locatieomschrijving')}>
                  Locatieomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('locatieomschrijving')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {locatieonroerendezaakList.map((locatieonroerendezaak, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/locatieonroerendezaak/${locatieonroerendezaak.id}`} color="link" size="sm">
                      {locatieonroerendezaak.id}
                    </Button>
                  </td>
                  <td>{locatieonroerendezaak.adrestype}</td>
                  <td>{locatieonroerendezaak.cultuurcodebebouwd}</td>
                  <td>
                    {locatieonroerendezaak.datumbegingeldigheid ? (
                      <TextFormat type="date" value={locatieonroerendezaak.datumbegingeldigheid} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {locatieonroerendezaak.datumeindegeldigheid ? (
                      <TextFormat type="date" value={locatieonroerendezaak.datumeindegeldigheid} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{locatieonroerendezaak.geometrie}</td>
                  <td>{locatieonroerendezaak.locatieomschrijving}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/locatieonroerendezaak/${locatieonroerendezaak.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/locatieonroerendezaak/${locatieonroerendezaak.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/locatieonroerendezaak/${locatieonroerendezaak.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Locatieonroerendezaaks found</div>
        )}
      </div>
    </div>
  );
};

export default Locatieonroerendezaak;
