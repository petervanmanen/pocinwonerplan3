import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './binnenlocatie.reducer';

export const Binnenlocatie = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const binnenlocatieList = useAppSelector(state => state.binnenlocatie.entities);
  const loading = useAppSelector(state => state.binnenlocatie.loading);

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
      <h2 id="binnenlocatie-heading" data-cy="BinnenlocatieHeading">
        Binnenlocaties
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/binnenlocatie/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Binnenlocatie
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {binnenlocatieList && binnenlocatieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('adres')}>
                  Adres <FontAwesomeIcon icon={getSortIconByFieldName('adres')} />
                </th>
                <th className="hand" onClick={sort('bouwjaar')}>
                  Bouwjaar <FontAwesomeIcon icon={getSortIconByFieldName('bouwjaar')} />
                </th>
                <th className="hand" onClick={sort('gemeentelijk')}>
                  Gemeentelijk <FontAwesomeIcon icon={getSortIconByFieldName('gemeentelijk')} />
                </th>
                <th className="hand" onClick={sort('geschattekostenperjaar')}>
                  Geschattekostenperjaar <FontAwesomeIcon icon={getSortIconByFieldName('geschattekostenperjaar')} />
                </th>
                <th className="hand" onClick={sort('gymzaal')}>
                  Gymzaal <FontAwesomeIcon icon={getSortIconByFieldName('gymzaal')} />
                </th>
                <th className="hand" onClick={sort('klokurenonderwijs')}>
                  Klokurenonderwijs <FontAwesomeIcon icon={getSortIconByFieldName('klokurenonderwijs')} />
                </th>
                <th className="hand" onClick={sort('klokurenverenigingen')}>
                  Klokurenverenigingen <FontAwesomeIcon icon={getSortIconByFieldName('klokurenverenigingen')} />
                </th>
                <th className="hand" onClick={sort('locatie')}>
                  Locatie <FontAwesomeIcon icon={getSortIconByFieldName('locatie')} />
                </th>
                <th className="hand" onClick={sort('onderhoudsniveau')}>
                  Onderhoudsniveau <FontAwesomeIcon icon={getSortIconByFieldName('onderhoudsniveau')} />
                </th>
                <th className="hand" onClick={sort('onderhoudsstatus')}>
                  Onderhoudsstatus <FontAwesomeIcon icon={getSortIconByFieldName('onderhoudsstatus')} />
                </th>
                <th className="hand" onClick={sort('sporthal')}>
                  Sporthal <FontAwesomeIcon icon={getSortIconByFieldName('sporthal')} />
                </th>
                <th className="hand" onClick={sort('vloeroppervlakte')}>
                  Vloeroppervlakte <FontAwesomeIcon icon={getSortIconByFieldName('vloeroppervlakte')} />
                </th>
                <th>
                  Isgevestigdin Verblijfsobject <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Bedient Wijk <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Belijning <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Sportmateriaal <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {binnenlocatieList.map((binnenlocatie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/binnenlocatie/${binnenlocatie.id}`} color="link" size="sm">
                      {binnenlocatie.id}
                    </Button>
                  </td>
                  <td>{binnenlocatie.adres}</td>
                  <td>{binnenlocatie.bouwjaar}</td>
                  <td>{binnenlocatie.gemeentelijk ? 'true' : 'false'}</td>
                  <td>{binnenlocatie.geschattekostenperjaar}</td>
                  <td>{binnenlocatie.gymzaal}</td>
                  <td>{binnenlocatie.klokurenonderwijs}</td>
                  <td>{binnenlocatie.klokurenverenigingen}</td>
                  <td>{binnenlocatie.locatie}</td>
                  <td>{binnenlocatie.onderhoudsniveau}</td>
                  <td>{binnenlocatie.onderhoudsstatus}</td>
                  <td>{binnenlocatie.sporthal}</td>
                  <td>{binnenlocatie.vloeroppervlakte}</td>
                  <td>
                    {binnenlocatie.isgevestigdinVerblijfsobject ? (
                      <Link to={`/verblijfsobject/${binnenlocatie.isgevestigdinVerblijfsobject.id}`}>
                        {binnenlocatie.isgevestigdinVerblijfsobject.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {binnenlocatie.bedientWijk ? (
                      <Link to={`/wijk/${binnenlocatie.bedientWijk.id}`}>{binnenlocatie.bedientWijk.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {binnenlocatie.heeftBelijnings
                      ? binnenlocatie.heeftBelijnings.map((val, j) => (
                          <span key={j}>
                            <Link to={`/belijning/${val.id}`}>{val.id}</Link>
                            {j === binnenlocatie.heeftBelijnings.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {binnenlocatie.heeftSportmateriaals
                      ? binnenlocatie.heeftSportmateriaals.map((val, j) => (
                          <span key={j}>
                            <Link to={`/sportmateriaal/${val.id}`}>{val.id}</Link>
                            {j === binnenlocatie.heeftSportmateriaals.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/binnenlocatie/${binnenlocatie.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/binnenlocatie/${binnenlocatie.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/binnenlocatie/${binnenlocatie.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Binnenlocaties found</div>
        )}
      </div>
    </div>
  );
};

export default Binnenlocatie;
