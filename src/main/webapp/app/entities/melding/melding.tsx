import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './melding.reducer';

export const Melding = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const meldingList = useAppSelector(state => state.melding.entities);
  const loading = useAppSelector(state => state.melding.loading);

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
      <h2 id="melding-heading" data-cy="MeldingHeading">
        Meldings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/melding/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Melding
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {meldingList && meldingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('vierentwintiguurs')}>
                  Vierentwintiguurs <FontAwesomeIcon icon={getSortIconByFieldName('vierentwintiguurs')} />
                </th>
                <th className="hand" onClick={sort('datumtijd')}>
                  Datumtijd <FontAwesomeIcon icon={getSortIconByFieldName('datumtijd')} />
                </th>
                <th className="hand" onClick={sort('illegaal')}>
                  Illegaal <FontAwesomeIcon icon={getSortIconByFieldName('illegaal')} />
                </th>
                <th className="hand" onClick={sort('meldingnummer')}>
                  Meldingnummer <FontAwesomeIcon icon={getSortIconByFieldName('meldingnummer')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th>
                  Hoofdcategorie Categorie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Subcategorie Categorie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Containertype <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Fractie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Locatie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Melder Medewerker <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Uitvoerder Leverancier <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Uitvoerder Medewerker <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Beheerobject <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Schouwronde <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {meldingList.map((melding, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/melding/${melding.id}`} color="link" size="sm">
                      {melding.id}
                    </Button>
                  </td>
                  <td>{melding.vierentwintiguurs}</td>
                  <td>{melding.datumtijd}</td>
                  <td>{melding.illegaal}</td>
                  <td>{melding.meldingnummer}</td>
                  <td>{melding.omschrijving}</td>
                  <td>
                    {melding.hoofdcategorieCategorie ? (
                      <Link to={`/categorie/${melding.hoofdcategorieCategorie.id}`}>{melding.hoofdcategorieCategorie.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {melding.subcategorieCategorie ? (
                      <Link to={`/categorie/${melding.subcategorieCategorie.id}`}>{melding.subcategorieCategorie.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {melding.betreftContainertype ? (
                      <Link to={`/containertype/${melding.betreftContainertype.id}`}>{melding.betreftContainertype.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {melding.betreftFractie ? <Link to={`/fractie/${melding.betreftFractie.id}`}>{melding.betreftFractie.id}</Link> : ''}
                  </td>
                  <td>
                    {melding.betreftLocatie ? <Link to={`/locatie/${melding.betreftLocatie.id}`}>{melding.betreftLocatie.id}</Link> : ''}
                  </td>
                  <td>
                    {melding.melderMedewerker ? (
                      <Link to={`/medewerker/${melding.melderMedewerker.id}`}>{melding.melderMedewerker.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {melding.uitvoerderLeverancier ? (
                      <Link to={`/leverancier/${melding.uitvoerderLeverancier.id}`}>{melding.uitvoerderLeverancier.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {melding.uitvoerderMedewerker ? (
                      <Link to={`/medewerker/${melding.uitvoerderMedewerker.id}`}>{melding.uitvoerderMedewerker.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {melding.betreftBeheerobjects
                      ? melding.betreftBeheerobjects.map((val, j) => (
                          <span key={j}>
                            <Link to={`/beheerobject/${val.id}`}>{val.id}</Link>
                            {j === melding.betreftBeheerobjects.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {melding.heeftSchouwronde ? (
                      <Link to={`/schouwronde/${melding.heeftSchouwronde.id}`}>{melding.heeftSchouwronde.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/melding/${melding.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/melding/${melding.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/melding/${melding.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Meldings found</div>
        )}
      </div>
    </div>
  );
};

export default Melding;
