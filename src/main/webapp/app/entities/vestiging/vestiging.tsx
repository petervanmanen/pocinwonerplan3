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

import { getEntities } from './vestiging.reducer';

export const Vestiging = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const vestigingList = useAppSelector(state => state.vestiging.entities);
  const loading = useAppSelector(state => state.vestiging.loading);

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
      <h2 id="vestiging-heading" data-cy="VestigingHeading">
        Vestigings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/vestiging/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Vestiging
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vestigingList && vestigingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('commercielevestiging')}>
                  Commercielevestiging <FontAwesomeIcon icon={getSortIconByFieldName('commercielevestiging')} />
                </th>
                <th className="hand" onClick={sort('datumaanvang')}>
                  Datumaanvang <FontAwesomeIcon icon={getSortIconByFieldName('datumaanvang')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumvoortzetting')}>
                  Datumvoortzetting <FontAwesomeIcon icon={getSortIconByFieldName('datumvoortzetting')} />
                </th>
                <th className="hand" onClick={sort('fulltimewerkzamemannen')}>
                  Fulltimewerkzamemannen <FontAwesomeIcon icon={getSortIconByFieldName('fulltimewerkzamemannen')} />
                </th>
                <th className="hand" onClick={sort('fulltimewerkzamevrouwen')}>
                  Fulltimewerkzamevrouwen <FontAwesomeIcon icon={getSortIconByFieldName('fulltimewerkzamevrouwen')} />
                </th>
                <th className="hand" onClick={sort('handelsnaam')}>
                  Handelsnaam <FontAwesomeIcon icon={getSortIconByFieldName('handelsnaam')} />
                </th>
                <th className="hand" onClick={sort('parttimewerkzamemannen')}>
                  Parttimewerkzamemannen <FontAwesomeIcon icon={getSortIconByFieldName('parttimewerkzamemannen')} />
                </th>
                <th className="hand" onClick={sort('parttimewerkzamevrouwen')}>
                  Parttimewerkzamevrouwen <FontAwesomeIcon icon={getSortIconByFieldName('parttimewerkzamevrouwen')} />
                </th>
                <th className="hand" onClick={sort('toevoegingadres')}>
                  Toevoegingadres <FontAwesomeIcon icon={getSortIconByFieldName('toevoegingadres')} />
                </th>
                <th className="hand" onClick={sort('totaalwerkzamepersonen')}>
                  Totaalwerkzamepersonen <FontAwesomeIcon icon={getSortIconByFieldName('totaalwerkzamepersonen')} />
                </th>
                <th className="hand" onClick={sort('verkortenaam')}>
                  Verkortenaam <FontAwesomeIcon icon={getSortIconByFieldName('verkortenaam')} />
                </th>
                <th className="hand" onClick={sort('vestigingsnummer')}>
                  Vestigingsnummer <FontAwesomeIcon icon={getSortIconByFieldName('vestigingsnummer')} />
                </th>
                <th>
                  Heeft Werkgelegenheid <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeftalslocatieadres Nummeraanduiding <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vestigingList.map((vestiging, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vestiging/${vestiging.id}`} color="link" size="sm">
                      {vestiging.id}
                    </Button>
                  </td>
                  <td>{vestiging.commercielevestiging}</td>
                  <td>
                    {vestiging.datumaanvang ? (
                      <TextFormat type="date" value={vestiging.datumaanvang} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {vestiging.datumeinde ? <TextFormat type="date" value={vestiging.datumeinde} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {vestiging.datumvoortzetting ? (
                      <TextFormat type="date" value={vestiging.datumvoortzetting} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{vestiging.fulltimewerkzamemannen}</td>
                  <td>{vestiging.fulltimewerkzamevrouwen}</td>
                  <td>{vestiging.handelsnaam}</td>
                  <td>{vestiging.parttimewerkzamemannen}</td>
                  <td>{vestiging.parttimewerkzamevrouwen}</td>
                  <td>{vestiging.toevoegingadres}</td>
                  <td>{vestiging.totaalwerkzamepersonen}</td>
                  <td>{vestiging.verkortenaam}</td>
                  <td>{vestiging.vestigingsnummer}</td>
                  <td>
                    {vestiging.heeftWerkgelegenheid ? (
                      <Link to={`/werkgelegenheid/${vestiging.heeftWerkgelegenheid.id}`}>{vestiging.heeftWerkgelegenheid.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {vestiging.heeftalslocatieadresNummeraanduiding ? (
                      <Link to={`/nummeraanduiding/${vestiging.heeftalslocatieadresNummeraanduiding.id}`}>
                        {vestiging.heeftalslocatieadresNummeraanduiding.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vestiging/${vestiging.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/vestiging/${vestiging.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/vestiging/${vestiging.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Vestigings found</div>
        )}
      </div>
    </div>
  );
};

export default Vestiging;
