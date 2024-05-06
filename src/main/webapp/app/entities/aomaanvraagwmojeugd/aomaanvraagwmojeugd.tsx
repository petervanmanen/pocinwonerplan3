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

import { getEntities } from './aomaanvraagwmojeugd.reducer';

export const Aomaanvraagwmojeugd = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const aomaanvraagwmojeugdList = useAppSelector(state => state.aomaanvraagwmojeugd.entities);
  const loading = useAppSelector(state => state.aomaanvraagwmojeugd.loading);

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
      <h2 id="aomaanvraagwmojeugd-heading" data-cy="AomaanvraagwmojeugdHeading">
        Aomaanvraagwmojeugds
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/aomaanvraagwmojeugd/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Aomaanvraagwmojeugd
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {aomaanvraagwmojeugdList && aomaanvraagwmojeugdList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('clientreactie')}>
                  Clientreactie <FontAwesomeIcon icon={getSortIconByFieldName('clientreactie')} />
                </th>
                <th className="hand" onClick={sort('datumbeschikking')}>
                  Datumbeschikking <FontAwesomeIcon icon={getSortIconByFieldName('datumbeschikking')} />
                </th>
                <th className="hand" onClick={sort('datumeersteafspraak')}>
                  Datumeersteafspraak <FontAwesomeIcon icon={getSortIconByFieldName('datumeersteafspraak')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumplanvastgesteld')}>
                  Datumplanvastgesteld <FontAwesomeIcon icon={getSortIconByFieldName('datumplanvastgesteld')} />
                </th>
                <th className="hand" onClick={sort('datumstartaanvraag')}>
                  Datumstartaanvraag <FontAwesomeIcon icon={getSortIconByFieldName('datumstartaanvraag')} />
                </th>
                <th className="hand" onClick={sort('deskundigheid')}>
                  Deskundigheid <FontAwesomeIcon icon={getSortIconByFieldName('deskundigheid')} />
                </th>
                <th className="hand" onClick={sort('doorloopmethodiek')}>
                  Doorloopmethodiek <FontAwesomeIcon icon={getSortIconByFieldName('doorloopmethodiek')} />
                </th>
                <th className="hand" onClick={sort('maximaledoorlooptijd')}>
                  Maximaledoorlooptijd <FontAwesomeIcon icon={getSortIconByFieldName('maximaledoorlooptijd')} />
                </th>
                <th className="hand" onClick={sort('redenafsluiting')}>
                  Redenafsluiting <FontAwesomeIcon icon={getSortIconByFieldName('redenafsluiting')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {aomaanvraagwmojeugdList.map((aomaanvraagwmojeugd, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/aomaanvraagwmojeugd/${aomaanvraagwmojeugd.id}`} color="link" size="sm">
                      {aomaanvraagwmojeugd.id}
                    </Button>
                  </td>
                  <td>{aomaanvraagwmojeugd.clientreactie}</td>
                  <td>
                    {aomaanvraagwmojeugd.datumbeschikking ? (
                      <TextFormat type="date" value={aomaanvraagwmojeugd.datumbeschikking} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {aomaanvraagwmojeugd.datumeersteafspraak ? (
                      <TextFormat type="date" value={aomaanvraagwmojeugd.datumeersteafspraak} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {aomaanvraagwmojeugd.datumeinde ? (
                      <TextFormat type="date" value={aomaanvraagwmojeugd.datumeinde} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {aomaanvraagwmojeugd.datumplanvastgesteld ? (
                      <TextFormat type="date" value={aomaanvraagwmojeugd.datumplanvastgesteld} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {aomaanvraagwmojeugd.datumstartaanvraag ? (
                      <TextFormat type="date" value={aomaanvraagwmojeugd.datumstartaanvraag} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{aomaanvraagwmojeugd.deskundigheid}</td>
                  <td>{aomaanvraagwmojeugd.doorloopmethodiek}</td>
                  <td>{aomaanvraagwmojeugd.maximaledoorlooptijd}</td>
                  <td>{aomaanvraagwmojeugd.redenafsluiting}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/aomaanvraagwmojeugd/${aomaanvraagwmojeugd.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/aomaanvraagwmojeugd/${aomaanvraagwmojeugd.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/aomaanvraagwmojeugd/${aomaanvraagwmojeugd.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Aomaanvraagwmojeugds found</div>
        )}
      </div>
    </div>
  );
};

export default Aomaanvraagwmojeugd;
