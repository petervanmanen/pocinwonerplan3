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

import { getEntities } from './verzoekomtoewijzing.reducer';

export const Verzoekomtoewijzing = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const verzoekomtoewijzingList = useAppSelector(state => state.verzoekomtoewijzing.entities);
  const loading = useAppSelector(state => state.verzoekomtoewijzing.loading);

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
      <h2 id="verzoekomtoewijzing-heading" data-cy="VerzoekomtoewijzingHeading">
        Verzoekomtoewijzings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/verzoekomtoewijzing/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Verzoekomtoewijzing
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {verzoekomtoewijzingList && verzoekomtoewijzingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('beschikkingsnummer')}>
                  Beschikkingsnummer <FontAwesomeIcon icon={getSortIconByFieldName('beschikkingsnummer')} />
                </th>
                <th className="hand" onClick={sort('commentaar')}>
                  Commentaar <FontAwesomeIcon icon={getSortIconByFieldName('commentaar')} />
                </th>
                <th className="hand" onClick={sort('datumeindetoewijzing')}>
                  Datumeindetoewijzing <FontAwesomeIcon icon={getSortIconByFieldName('datumeindetoewijzing')} />
                </th>
                <th className="hand" onClick={sort('datumingangbeschikking')}>
                  Datumingangbeschikking <FontAwesomeIcon icon={getSortIconByFieldName('datumingangbeschikking')} />
                </th>
                <th className="hand" onClick={sort('datumingangtoewijzing')}>
                  Datumingangtoewijzing <FontAwesomeIcon icon={getSortIconByFieldName('datumingangtoewijzing')} />
                </th>
                <th className="hand" onClick={sort('datumontvangst')}>
                  Datumontvangst <FontAwesomeIcon icon={getSortIconByFieldName('datumontvangst')} />
                </th>
                <th className="hand" onClick={sort('eenheid')}>
                  Eenheid <FontAwesomeIcon icon={getSortIconByFieldName('eenheid')} />
                </th>
                <th className="hand" onClick={sort('frequentie')}>
                  Frequentie <FontAwesomeIcon icon={getSortIconByFieldName('frequentie')} />
                </th>
                <th className="hand" onClick={sort('raamcontract')}>
                  Raamcontract <FontAwesomeIcon icon={getSortIconByFieldName('raamcontract')} />
                </th>
                <th className="hand" onClick={sort('referentieaanbieder')}>
                  Referentieaanbieder <FontAwesomeIcon icon={getSortIconByFieldName('referentieaanbieder')} />
                </th>
                <th className="hand" onClick={sort('soortverwijzer')}>
                  Soortverwijzer <FontAwesomeIcon icon={getSortIconByFieldName('soortverwijzer')} />
                </th>
                <th className="hand" onClick={sort('verwijzer')}>
                  Verwijzer <FontAwesomeIcon icon={getSortIconByFieldName('verwijzer')} />
                </th>
                <th className="hand" onClick={sort('volume')}>
                  Volume <FontAwesomeIcon icon={getSortIconByFieldName('volume')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {verzoekomtoewijzingList.map((verzoekomtoewijzing, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/verzoekomtoewijzing/${verzoekomtoewijzing.id}`} color="link" size="sm">
                      {verzoekomtoewijzing.id}
                    </Button>
                  </td>
                  <td>{verzoekomtoewijzing.beschikkingsnummer}</td>
                  <td>{verzoekomtoewijzing.commentaar}</td>
                  <td>
                    {verzoekomtoewijzing.datumeindetoewijzing ? (
                      <TextFormat type="date" value={verzoekomtoewijzing.datumeindetoewijzing} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {verzoekomtoewijzing.datumingangbeschikking ? (
                      <TextFormat type="date" value={verzoekomtoewijzing.datumingangbeschikking} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {verzoekomtoewijzing.datumingangtoewijzing ? (
                      <TextFormat type="date" value={verzoekomtoewijzing.datumingangtoewijzing} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {verzoekomtoewijzing.datumontvangst ? (
                      <TextFormat type="date" value={verzoekomtoewijzing.datumontvangst} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{verzoekomtoewijzing.eenheid}</td>
                  <td>{verzoekomtoewijzing.frequentie}</td>
                  <td>{verzoekomtoewijzing.raamcontract ? 'true' : 'false'}</td>
                  <td>{verzoekomtoewijzing.referentieaanbieder}</td>
                  <td>{verzoekomtoewijzing.soortverwijzer}</td>
                  <td>{verzoekomtoewijzing.verwijzer}</td>
                  <td>{verzoekomtoewijzing.volume}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/verzoekomtoewijzing/${verzoekomtoewijzing.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/verzoekomtoewijzing/${verzoekomtoewijzing.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/verzoekomtoewijzing/${verzoekomtoewijzing.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Verzoekomtoewijzings found</div>
        )}
      </div>
    </div>
  );
};

export default Verzoekomtoewijzing;
