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

import { getEntities } from './procesverbaalonderwijs.reducer';

export const Procesverbaalonderwijs = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const procesverbaalonderwijsList = useAppSelector(state => state.procesverbaalonderwijs.entities);
  const loading = useAppSelector(state => state.procesverbaalonderwijs.loading);

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
      <h2 id="procesverbaalonderwijs-heading" data-cy="ProcesverbaalonderwijsHeading">
        Procesverbaalonderwijs
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/procesverbaalonderwijs/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Procesverbaalonderwijs
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {procesverbaalonderwijsList && procesverbaalonderwijsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumafgehandeld')}>
                  Datumafgehandeld <FontAwesomeIcon icon={getSortIconByFieldName('datumafgehandeld')} />
                </th>
                <th className="hand" onClick={sort('datumeindeproeftijd')}>
                  Datumeindeproeftijd <FontAwesomeIcon icon={getSortIconByFieldName('datumeindeproeftijd')} />
                </th>
                <th className="hand" onClick={sort('datumingelicht')}>
                  Datumingelicht <FontAwesomeIcon icon={getSortIconByFieldName('datumingelicht')} />
                </th>
                <th className="hand" onClick={sort('datumuitspraak')}>
                  Datumuitspraak <FontAwesomeIcon icon={getSortIconByFieldName('datumuitspraak')} />
                </th>
                <th className="hand" onClick={sort('datumzitting')}>
                  Datumzitting <FontAwesomeIcon icon={getSortIconByFieldName('datumzitting')} />
                </th>
                <th className="hand" onClick={sort('geldboete')}>
                  Geldboete <FontAwesomeIcon icon={getSortIconByFieldName('geldboete')} />
                </th>
                <th className="hand" onClick={sort('geldboetevoorwaardelijk')}>
                  Geldboetevoorwaardelijk <FontAwesomeIcon icon={getSortIconByFieldName('geldboetevoorwaardelijk')} />
                </th>
                <th className="hand" onClick={sort('opmerkingen')}>
                  Opmerkingen <FontAwesomeIcon icon={getSortIconByFieldName('opmerkingen')} />
                </th>
                <th className="hand" onClick={sort('proeftijd')}>
                  Proeftijd <FontAwesomeIcon icon={getSortIconByFieldName('proeftijd')} />
                </th>
                <th className="hand" onClick={sort('reden')}>
                  Reden <FontAwesomeIcon icon={getSortIconByFieldName('reden')} />
                </th>
                <th className="hand" onClick={sort('sanctiesoort')}>
                  Sanctiesoort <FontAwesomeIcon icon={getSortIconByFieldName('sanctiesoort')} />
                </th>
                <th className="hand" onClick={sort('uitspraak')}>
                  Uitspraak <FontAwesomeIcon icon={getSortIconByFieldName('uitspraak')} />
                </th>
                <th className="hand" onClick={sort('verzuimsoort')}>
                  Verzuimsoort <FontAwesomeIcon icon={getSortIconByFieldName('verzuimsoort')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {procesverbaalonderwijsList.map((procesverbaalonderwijs, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/procesverbaalonderwijs/${procesverbaalonderwijs.id}`} color="link" size="sm">
                      {procesverbaalonderwijs.id}
                    </Button>
                  </td>
                  <td>
                    {procesverbaalonderwijs.datumafgehandeld ? (
                      <TextFormat type="date" value={procesverbaalonderwijs.datumafgehandeld} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {procesverbaalonderwijs.datumeindeproeftijd ? (
                      <TextFormat type="date" value={procesverbaalonderwijs.datumeindeproeftijd} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {procesverbaalonderwijs.datumingelicht ? (
                      <TextFormat type="date" value={procesverbaalonderwijs.datumingelicht} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {procesverbaalonderwijs.datumuitspraak ? (
                      <TextFormat type="date" value={procesverbaalonderwijs.datumuitspraak} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {procesverbaalonderwijs.datumzitting ? (
                      <TextFormat type="date" value={procesverbaalonderwijs.datumzitting} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{procesverbaalonderwijs.geldboete}</td>
                  <td>{procesverbaalonderwijs.geldboetevoorwaardelijk}</td>
                  <td>{procesverbaalonderwijs.opmerkingen}</td>
                  <td>{procesverbaalonderwijs.proeftijd}</td>
                  <td>{procesverbaalonderwijs.reden}</td>
                  <td>{procesverbaalonderwijs.sanctiesoort}</td>
                  <td>{procesverbaalonderwijs.uitspraak}</td>
                  <td>{procesverbaalonderwijs.verzuimsoort}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/procesverbaalonderwijs/${procesverbaalonderwijs.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/procesverbaalonderwijs/${procesverbaalonderwijs.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/procesverbaalonderwijs/${procesverbaalonderwijs.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Procesverbaalonderwijs found</div>
        )}
      </div>
    </div>
  );
};

export default Procesverbaalonderwijs;
