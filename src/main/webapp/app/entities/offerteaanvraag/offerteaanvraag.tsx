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

import { getEntities } from './offerteaanvraag.reducer';

export const Offerteaanvraag = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const offerteaanvraagList = useAppSelector(state => state.offerteaanvraag.entities);
  const loading = useAppSelector(state => state.offerteaanvraag.loading);

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
      <h2 id="offerteaanvraag-heading" data-cy="OfferteaanvraagHeading">
        Offerteaanvraags
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/offerteaanvraag/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Offerteaanvraag
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {offerteaanvraagList && offerteaanvraagList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumaanvraag')}>
                  Datumaanvraag <FontAwesomeIcon icon={getSortIconByFieldName('datumaanvraag')} />
                </th>
                <th className="hand" onClick={sort('datumsluiting')}>
                  Datumsluiting <FontAwesomeIcon icon={getSortIconByFieldName('datumsluiting')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th>
                  Betreft Aanbesteding <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Gerichtaan Leverancier <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {offerteaanvraagList.map((offerteaanvraag, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/offerteaanvraag/${offerteaanvraag.id}`} color="link" size="sm">
                      {offerteaanvraag.id}
                    </Button>
                  </td>
                  <td>
                    {offerteaanvraag.datumaanvraag ? (
                      <TextFormat type="date" value={offerteaanvraag.datumaanvraag} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{offerteaanvraag.datumsluiting}</td>
                  <td>{offerteaanvraag.naam}</td>
                  <td>{offerteaanvraag.omschrijving}</td>
                  <td>
                    {offerteaanvraag.betreftAanbesteding ? (
                      <Link to={`/aanbesteding/${offerteaanvraag.betreftAanbesteding.id}`}>{offerteaanvraag.betreftAanbesteding.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {offerteaanvraag.gerichtaanLeverancier ? (
                      <Link to={`/leverancier/${offerteaanvraag.gerichtaanLeverancier.id}`}>
                        {offerteaanvraag.gerichtaanLeverancier.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/offerteaanvraag/${offerteaanvraag.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/offerteaanvraag/${offerteaanvraag.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/offerteaanvraag/${offerteaanvraag.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Offerteaanvraags found</div>
        )}
      </div>
    </div>
  );
};

export default Offerteaanvraag;
