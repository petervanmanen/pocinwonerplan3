import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './artefact.reducer';

export const Artefact = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const artefactList = useAppSelector(state => state.artefact.entities);
  const loading = useAppSelector(state => state.artefact.loading);

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
      <h2 id="artefact-heading" data-cy="ArtefactHeading">
        Artefacts
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/artefact/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Artefact
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {artefactList && artefactList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('artefectnummer')}>
                  Artefectnummer <FontAwesomeIcon icon={getSortIconByFieldName('artefectnummer')} />
                </th>
                <th className="hand" onClick={sort('beschrijving')}>
                  Beschrijving <FontAwesomeIcon icon={getSortIconByFieldName('beschrijving')} />
                </th>
                <th className="hand" onClick={sort('conserveren')}>
                  Conserveren <FontAwesomeIcon icon={getSortIconByFieldName('conserveren')} />
                </th>
                <th className="hand" onClick={sort('datering')}>
                  Datering <FontAwesomeIcon icon={getSortIconByFieldName('datering')} />
                </th>
                <th className="hand" onClick={sort('dateringcomplex')}>
                  Dateringcomplex <FontAwesomeIcon icon={getSortIconByFieldName('dateringcomplex')} />
                </th>
                <th className="hand" onClick={sort('determinatieniveau')}>
                  Determinatieniveau <FontAwesomeIcon icon={getSortIconByFieldName('determinatieniveau')} />
                </th>
                <th className="hand" onClick={sort('dianummer')}>
                  Dianummer <FontAwesomeIcon icon={getSortIconByFieldName('dianummer')} />
                </th>
                <th className="hand" onClick={sort('doosnummer')}>
                  Doosnummer <FontAwesomeIcon icon={getSortIconByFieldName('doosnummer')} />
                </th>
                <th className="hand" onClick={sort('exposabel')}>
                  Exposabel <FontAwesomeIcon icon={getSortIconByFieldName('exposabel')} />
                </th>
                <th className="hand" onClick={sort('fotonummer')}>
                  Fotonummer <FontAwesomeIcon icon={getSortIconByFieldName('fotonummer')} />
                </th>
                <th className="hand" onClick={sort('functie')}>
                  Functie <FontAwesomeIcon icon={getSortIconByFieldName('functie')} />
                </th>
                <th className="hand" onClick={sort('herkomst')}>
                  Herkomst <FontAwesomeIcon icon={getSortIconByFieldName('herkomst')} />
                </th>
                <th className="hand" onClick={sort('key')}>
                  Key <FontAwesomeIcon icon={getSortIconByFieldName('key')} />
                </th>
                <th className="hand" onClick={sort('keydoos')}>
                  Keydoos <FontAwesomeIcon icon={getSortIconByFieldName('keydoos')} />
                </th>
                <th className="hand" onClick={sort('keymagazijnplaatsing')}>
                  Keymagazijnplaatsing <FontAwesomeIcon icon={getSortIconByFieldName('keymagazijnplaatsing')} />
                </th>
                <th className="hand" onClick={sort('keyput')}>
                  Keyput <FontAwesomeIcon icon={getSortIconByFieldName('keyput')} />
                </th>
                <th className="hand" onClick={sort('keyvondst')}>
                  Keyvondst <FontAwesomeIcon icon={getSortIconByFieldName('keyvondst')} />
                </th>
                <th className="hand" onClick={sort('literatuur')}>
                  Literatuur <FontAwesomeIcon icon={getSortIconByFieldName('literatuur')} />
                </th>
                <th className="hand" onClick={sort('maten')}>
                  Maten <FontAwesomeIcon icon={getSortIconByFieldName('maten')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('opmerkingen')}>
                  Opmerkingen <FontAwesomeIcon icon={getSortIconByFieldName('opmerkingen')} />
                </th>
                <th className="hand" onClick={sort('origine')}>
                  Origine <FontAwesomeIcon icon={getSortIconByFieldName('origine')} />
                </th>
                <th className="hand" onClick={sort('projectcd')}>
                  Projectcd <FontAwesomeIcon icon={getSortIconByFieldName('projectcd')} />
                </th>
                <th className="hand" onClick={sort('putnummer')}>
                  Putnummer <FontAwesomeIcon icon={getSortIconByFieldName('putnummer')} />
                </th>
                <th className="hand" onClick={sort('restauratiewenselijk')}>
                  Restauratiewenselijk <FontAwesomeIcon icon={getSortIconByFieldName('restauratiewenselijk')} />
                </th>
                <th className="hand" onClick={sort('tekeningnummer')}>
                  Tekeningnummer <FontAwesomeIcon icon={getSortIconByFieldName('tekeningnummer')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('vondstnummer')}>
                  Vondstnummer <FontAwesomeIcon icon={getSortIconByFieldName('vondstnummer')} />
                </th>
                <th>
                  Zitin Doos <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Isvansoort Artefactsoort <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Bevat Vondst <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {artefactList.map((artefact, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/artefact/${artefact.id}`} color="link" size="sm">
                      {artefact.id}
                    </Button>
                  </td>
                  <td>{artefact.artefectnummer}</td>
                  <td>{artefact.beschrijving}</td>
                  <td>{artefact.conserveren ? 'true' : 'false'}</td>
                  <td>{artefact.datering}</td>
                  <td>{artefact.dateringcomplex}</td>
                  <td>{artefact.determinatieniveau}</td>
                  <td>{artefact.dianummer}</td>
                  <td>{artefact.doosnummer}</td>
                  <td>{artefact.exposabel ? 'true' : 'false'}</td>
                  <td>{artefact.fotonummer}</td>
                  <td>{artefact.functie}</td>
                  <td>{artefact.herkomst}</td>
                  <td>{artefact.key}</td>
                  <td>{artefact.keydoos}</td>
                  <td>{artefact.keymagazijnplaatsing}</td>
                  <td>{artefact.keyput}</td>
                  <td>{artefact.keyvondst}</td>
                  <td>{artefact.literatuur}</td>
                  <td>{artefact.maten}</td>
                  <td>{artefact.naam}</td>
                  <td>{artefact.opmerkingen}</td>
                  <td>{artefact.origine}</td>
                  <td>{artefact.projectcd}</td>
                  <td>{artefact.putnummer}</td>
                  <td>{artefact.restauratiewenselijk ? 'true' : 'false'}</td>
                  <td>{artefact.tekeningnummer}</td>
                  <td>{artefact.type}</td>
                  <td>{artefact.vondstnummer}</td>
                  <td>{artefact.zitinDoos ? <Link to={`/doos/${artefact.zitinDoos.id}`}>{artefact.zitinDoos.id}</Link> : ''}</td>
                  <td>
                    {artefact.isvansoortArtefactsoort ? (
                      <Link to={`/artefactsoort/${artefact.isvansoortArtefactsoort.id}`}>{artefact.isvansoortArtefactsoort.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{artefact.bevatVondst ? <Link to={`/vondst/${artefact.bevatVondst.id}`}>{artefact.bevatVondst.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/artefact/${artefact.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/artefact/${artefact.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/artefact/${artefact.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Artefacts found</div>
        )}
      </div>
    </div>
  );
};

export default Artefact;
