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

import { getEntities } from './toewijzing.reducer';

export const Toewijzing = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const toewijzingList = useAppSelector(state => state.toewijzing.entities);
  const loading = useAppSelector(state => state.toewijzing.loading);

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
      <h2 id="toewijzing-heading" data-cy="ToewijzingHeading">
        Toewijzings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/toewijzing/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Toewijzing
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {toewijzingList && toewijzingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  Code <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('commentaar')}>
                  Commentaar <FontAwesomeIcon icon={getSortIconByFieldName('commentaar')} />
                </th>
                <th className="hand" onClick={sort('datumaanschaf')}>
                  Datumaanschaf <FontAwesomeIcon icon={getSortIconByFieldName('datumaanschaf')} />
                </th>
                <th className="hand" onClick={sort('datumeindetoewijzing')}>
                  Datumeindetoewijzing <FontAwesomeIcon icon={getSortIconByFieldName('datumeindetoewijzing')} />
                </th>
                <th className="hand" onClick={sort('datumstarttoewijzing')}>
                  Datumstarttoewijzing <FontAwesomeIcon icon={getSortIconByFieldName('datumstarttoewijzing')} />
                </th>
                <th className="hand" onClick={sort('datumtoewijzing')}>
                  Datumtoewijzing <FontAwesomeIcon icon={getSortIconByFieldName('datumtoewijzing')} />
                </th>
                <th className="hand" onClick={sort('eenheid')}>
                  Eenheid <FontAwesomeIcon icon={getSortIconByFieldName('eenheid')} />
                </th>
                <th className="hand" onClick={sort('frequentie')}>
                  Frequentie <FontAwesomeIcon icon={getSortIconByFieldName('frequentie')} />
                </th>
                <th className="hand" onClick={sort('omvang')}>
                  Omvang <FontAwesomeIcon icon={getSortIconByFieldName('omvang')} />
                </th>
                <th className="hand" onClick={sort('redenwijziging')}>
                  Redenwijziging <FontAwesomeIcon icon={getSortIconByFieldName('redenwijziging')} />
                </th>
                <th className="hand" onClick={sort('toewijzingnummer')}>
                  Toewijzingnummer <FontAwesomeIcon icon={getSortIconByFieldName('toewijzingnummer')} />
                </th>
                <th className="hand" onClick={sort('wet')}>
                  Wet <FontAwesomeIcon icon={getSortIconByFieldName('wet')} />
                </th>
                <th>
                  Levertvoorziening Leverancier <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Toewijzing Beschikking <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {toewijzingList.map((toewijzing, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/toewijzing/${toewijzing.id}`} color="link" size="sm">
                      {toewijzing.id}
                    </Button>
                  </td>
                  <td>{toewijzing.code}</td>
                  <td>{toewijzing.commentaar}</td>
                  <td>
                    {toewijzing.datumaanschaf ? (
                      <TextFormat type="date" value={toewijzing.datumaanschaf} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {toewijzing.datumeindetoewijzing ? (
                      <TextFormat type="date" value={toewijzing.datumeindetoewijzing} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {toewijzing.datumstarttoewijzing ? (
                      <TextFormat type="date" value={toewijzing.datumstarttoewijzing} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {toewijzing.datumtoewijzing ? (
                      <TextFormat type="date" value={toewijzing.datumtoewijzing} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{toewijzing.eenheid}</td>
                  <td>{toewijzing.frequentie}</td>
                  <td>{toewijzing.omvang}</td>
                  <td>{toewijzing.redenwijziging}</td>
                  <td>{toewijzing.toewijzingnummer}</td>
                  <td>{toewijzing.wet}</td>
                  <td>
                    {toewijzing.levertvoorzieningLeverancier ? (
                      <Link to={`/leverancier/${toewijzing.levertvoorzieningLeverancier.id}`}>
                        {toewijzing.levertvoorzieningLeverancier.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {toewijzing.toewijzingBeschikking ? (
                      <Link to={`/beschikking/${toewijzing.toewijzingBeschikking.id}`}>{toewijzing.toewijzingBeschikking.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/toewijzing/${toewijzing.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/toewijzing/${toewijzing.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/toewijzing/${toewijzing.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Toewijzings found</div>
        )}
      </div>
    </div>
  );
};

export default Toewijzing;
