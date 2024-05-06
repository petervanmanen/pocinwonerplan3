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

import { getEntities } from './redenverliesnationaliteit.reducer';

export const Redenverliesnationaliteit = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const redenverliesnationaliteitList = useAppSelector(state => state.redenverliesnationaliteit.entities);
  const loading = useAppSelector(state => state.redenverliesnationaliteit.loading);

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
      <h2 id="redenverliesnationaliteit-heading" data-cy="RedenverliesnationaliteitHeading">
        Redenverliesnationaliteits
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/redenverliesnationaliteit/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Redenverliesnationaliteit
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {redenverliesnationaliteitList && redenverliesnationaliteitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumaanvanggeldigheidverlies')}>
                  Datumaanvanggeldigheidverlies <FontAwesomeIcon icon={getSortIconByFieldName('datumaanvanggeldigheidverlies')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidverlies')}>
                  Datumeindegeldigheidverlies <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidverlies')} />
                </th>
                <th className="hand" onClick={sort('omschrijvingverlies')}>
                  Omschrijvingverlies <FontAwesomeIcon icon={getSortIconByFieldName('omschrijvingverlies')} />
                </th>
                <th className="hand" onClick={sort('redennummerverlies')}>
                  Redennummerverlies <FontAwesomeIcon icon={getSortIconByFieldName('redennummerverlies')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {redenverliesnationaliteitList.map((redenverliesnationaliteit, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/redenverliesnationaliteit/${redenverliesnationaliteit.id}`} color="link" size="sm">
                      {redenverliesnationaliteit.id}
                    </Button>
                  </td>
                  <td>
                    {redenverliesnationaliteit.datumaanvanggeldigheidverlies ? (
                      <TextFormat
                        type="date"
                        value={redenverliesnationaliteit.datumaanvanggeldigheidverlies}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>
                    {redenverliesnationaliteit.datumeindegeldigheidverlies ? (
                      <TextFormat
                        type="date"
                        value={redenverliesnationaliteit.datumeindegeldigheidverlies}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>{redenverliesnationaliteit.omschrijvingverlies}</td>
                  <td>{redenverliesnationaliteit.redennummerverlies}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/redenverliesnationaliteit/${redenverliesnationaliteit.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/redenverliesnationaliteit/${redenverliesnationaliteit.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/redenverliesnationaliteit/${redenverliesnationaliteit.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Redenverliesnationaliteits found</div>
        )}
      </div>
    </div>
  );
};

export default Redenverliesnationaliteit;
