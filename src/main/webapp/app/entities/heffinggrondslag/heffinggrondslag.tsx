import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './heffinggrondslag.reducer';

export const Heffinggrondslag = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const heffinggrondslagList = useAppSelector(state => state.heffinggrondslag.entities);
  const loading = useAppSelector(state => state.heffinggrondslag.loading);

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
      <h2 id="heffinggrondslag-heading" data-cy="HeffinggrondslagHeading">
        Heffinggrondslags
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/heffinggrondslag/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Heffinggrondslag
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {heffinggrondslagList && heffinggrondslagList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('bedrag')}>
                  Bedrag <FontAwesomeIcon icon={getSortIconByFieldName('bedrag')} />
                </th>
                <th className="hand" onClick={sort('domein')}>
                  Domein <FontAwesomeIcon icon={getSortIconByFieldName('domein')} />
                </th>
                <th className="hand" onClick={sort('hoofdstuk')}>
                  Hoofdstuk <FontAwesomeIcon icon={getSortIconByFieldName('hoofdstuk')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('paragraaf')}>
                  Paragraaf <FontAwesomeIcon icon={getSortIconByFieldName('paragraaf')} />
                </th>
                <th>
                  Vermeldin Heffingsverordening <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Zaaktype <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {heffinggrondslagList.map((heffinggrondslag, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/heffinggrondslag/${heffinggrondslag.id}`} color="link" size="sm">
                      {heffinggrondslag.id}
                    </Button>
                  </td>
                  <td>{heffinggrondslag.bedrag}</td>
                  <td>{heffinggrondslag.domein}</td>
                  <td>{heffinggrondslag.hoofdstuk}</td>
                  <td>{heffinggrondslag.omschrijving}</td>
                  <td>{heffinggrondslag.paragraaf}</td>
                  <td>
                    {heffinggrondslag.vermeldinHeffingsverordening ? (
                      <Link to={`/heffingsverordening/${heffinggrondslag.vermeldinHeffingsverordening.id}`}>
                        {heffinggrondslag.vermeldinHeffingsverordening.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {heffinggrondslag.heeftZaaktype ? (
                      <Link to={`/zaaktype/${heffinggrondslag.heeftZaaktype.id}`}>{heffinggrondslag.heeftZaaktype.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/heffinggrondslag/${heffinggrondslag.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/heffinggrondslag/${heffinggrondslag.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/heffinggrondslag/${heffinggrondslag.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Heffinggrondslags found</div>
        )}
      </div>
    </div>
  );
};

export default Heffinggrondslag;
