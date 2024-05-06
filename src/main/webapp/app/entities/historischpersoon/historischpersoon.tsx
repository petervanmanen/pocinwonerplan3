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

import { getEntities } from './historischpersoon.reducer';

export const Historischpersoon = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const historischpersoonList = useAppSelector(state => state.historischpersoon.entities);
  const loading = useAppSelector(state => state.historischpersoon.loading);

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
      <h2 id="historischpersoon-heading" data-cy="HistorischpersoonHeading">
        Historischpersoons
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/historischpersoon/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Historischpersoon
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {historischpersoonList && historischpersoonList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('beroep')}>
                  Beroep <FontAwesomeIcon icon={getSortIconByFieldName('beroep')} />
                </th>
                <th className="hand" onClick={sort('datumgeboorte')}>
                  Datumgeboorte <FontAwesomeIcon icon={getSortIconByFieldName('datumgeboorte')} />
                </th>
                <th className="hand" onClick={sort('datumoverlijden')}>
                  Datumoverlijden <FontAwesomeIcon icon={getSortIconByFieldName('datumoverlijden')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('publiektoegankelijk')}>
                  Publiektoegankelijk <FontAwesomeIcon icon={getSortIconByFieldName('publiektoegankelijk')} />
                </th>
                <th className="hand" onClick={sort('woondeop')}>
                  Woondeop <FontAwesomeIcon icon={getSortIconByFieldName('woondeop')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {historischpersoonList.map((historischpersoon, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/historischpersoon/${historischpersoon.id}`} color="link" size="sm">
                      {historischpersoon.id}
                    </Button>
                  </td>
                  <td>{historischpersoon.beroep}</td>
                  <td>
                    {historischpersoon.datumgeboorte ? (
                      <TextFormat type="date" value={historischpersoon.datumgeboorte} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {historischpersoon.datumoverlijden ? (
                      <TextFormat type="date" value={historischpersoon.datumoverlijden} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{historischpersoon.naam}</td>
                  <td>{historischpersoon.omschrijving}</td>
                  <td>{historischpersoon.publiektoegankelijk}</td>
                  <td>{historischpersoon.woondeop}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/historischpersoon/${historischpersoon.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/historischpersoon/${historischpersoon.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/historischpersoon/${historischpersoon.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Historischpersoons found</div>
        )}
      </div>
    </div>
  );
};

export default Historischpersoon;
