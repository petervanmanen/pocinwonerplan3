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

import { getEntities } from './gemeentebegrafenis.reducer';

export const Gemeentebegrafenis = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const gemeentebegrafenisList = useAppSelector(state => state.gemeentebegrafenis.entities);
  const loading = useAppSelector(state => state.gemeentebegrafenis.loading);

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
      <h2 id="gemeentebegrafenis-heading" data-cy="GemeentebegrafenisHeading">
        Gemeentebegrafenis
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/gemeentebegrafenis/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Gemeentebegrafenis
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {gemeentebegrafenisList && gemeentebegrafenisList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('achtergrondmelding')}>
                  Achtergrondmelding <FontAwesomeIcon icon={getSortIconByFieldName('achtergrondmelding')} />
                </th>
                <th className="hand" onClick={sort('begrafeniskosten')}>
                  Begrafeniskosten <FontAwesomeIcon icon={getSortIconByFieldName('begrafeniskosten')} />
                </th>
                <th className="hand" onClick={sort('datumafgedaan')}>
                  Datumafgedaan <FontAwesomeIcon icon={getSortIconByFieldName('datumafgedaan')} />
                </th>
                <th className="hand" onClick={sort('datumbegrafenis')}>
                  Datumbegrafenis <FontAwesomeIcon icon={getSortIconByFieldName('datumbegrafenis')} />
                </th>
                <th className="hand" onClick={sort('datumgemeld')}>
                  Datumgemeld <FontAwesomeIcon icon={getSortIconByFieldName('datumgemeld')} />
                </th>
                <th className="hand" onClick={sort('datumruiminggraf')}>
                  Datumruiminggraf <FontAwesomeIcon icon={getSortIconByFieldName('datumruiminggraf')} />
                </th>
                <th className="hand" onClick={sort('doodsoorzaak')}>
                  Doodsoorzaak <FontAwesomeIcon icon={getSortIconByFieldName('doodsoorzaak')} />
                </th>
                <th className="hand" onClick={sort('gemeentelijkekosten')}>
                  Gemeentelijkekosten <FontAwesomeIcon icon={getSortIconByFieldName('gemeentelijkekosten')} />
                </th>
                <th className="hand" onClick={sort('inkoopordernummer')}>
                  Inkoopordernummer <FontAwesomeIcon icon={getSortIconByFieldName('inkoopordernummer')} />
                </th>
                <th className="hand" onClick={sort('melder')}>
                  Melder <FontAwesomeIcon icon={getSortIconByFieldName('melder')} />
                </th>
                <th className="hand" onClick={sort('urengemeente')}>
                  Urengemeente <FontAwesomeIcon icon={getSortIconByFieldName('urengemeente')} />
                </th>
                <th className="hand" onClick={sort('verhaaldbedrag')}>
                  Verhaaldbedrag <FontAwesomeIcon icon={getSortIconByFieldName('verhaaldbedrag')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {gemeentebegrafenisList.map((gemeentebegrafenis, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/gemeentebegrafenis/${gemeentebegrafenis.id}`} color="link" size="sm">
                      {gemeentebegrafenis.id}
                    </Button>
                  </td>
                  <td>{gemeentebegrafenis.achtergrondmelding}</td>
                  <td>{gemeentebegrafenis.begrafeniskosten}</td>
                  <td>
                    {gemeentebegrafenis.datumafgedaan ? (
                      <TextFormat type="date" value={gemeentebegrafenis.datumafgedaan} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {gemeentebegrafenis.datumbegrafenis ? (
                      <TextFormat type="date" value={gemeentebegrafenis.datumbegrafenis} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {gemeentebegrafenis.datumgemeld ? (
                      <TextFormat type="date" value={gemeentebegrafenis.datumgemeld} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {gemeentebegrafenis.datumruiminggraf ? (
                      <TextFormat type="date" value={gemeentebegrafenis.datumruiminggraf} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{gemeentebegrafenis.doodsoorzaak}</td>
                  <td>{gemeentebegrafenis.gemeentelijkekosten}</td>
                  <td>{gemeentebegrafenis.inkoopordernummer}</td>
                  <td>{gemeentebegrafenis.melder}</td>
                  <td>{gemeentebegrafenis.urengemeente}</td>
                  <td>{gemeentebegrafenis.verhaaldbedrag}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/gemeentebegrafenis/${gemeentebegrafenis.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/gemeentebegrafenis/${gemeentebegrafenis.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/gemeentebegrafenis/${gemeentebegrafenis.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Gemeentebegrafenis found</div>
        )}
      </div>
    </div>
  );
};

export default Gemeentebegrafenis;
