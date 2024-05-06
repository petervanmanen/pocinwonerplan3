import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './startformulieraanbesteden.reducer';

export const Startformulieraanbesteden = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const startformulieraanbestedenList = useAppSelector(state => state.startformulieraanbesteden.entities);
  const loading = useAppSelector(state => state.startformulieraanbesteden.loading);

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
      <h2 id="startformulieraanbesteden-heading" data-cy="StartformulieraanbestedenHeading">
        Startformulieraanbestedens
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/startformulieraanbesteden/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Startformulieraanbesteden
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {startformulieraanbestedenList && startformulieraanbestedenList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('beoogdelooptijd')}>
                  Beoogdelooptijd <FontAwesomeIcon icon={getSortIconByFieldName('beoogdelooptijd')} />
                </th>
                <th className="hand" onClick={sort('beoogdetotaleopdrachtwaarde')}>
                  Beoogdetotaleopdrachtwaarde <FontAwesomeIcon icon={getSortIconByFieldName('beoogdetotaleopdrachtwaarde')} />
                </th>
                <th className="hand" onClick={sort('indicatieaanvullendeopdrachtleverancier')}>
                  Indicatieaanvullendeopdrachtleverancier{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('indicatieaanvullendeopdrachtleverancier')} />
                </th>
                <th className="hand" onClick={sort('indicatiebeoogdeaanbestedingonderhands')}>
                  Indicatiebeoogdeaanbestedingonderhands{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('indicatiebeoogdeaanbestedingonderhands')} />
                </th>
                <th className="hand" onClick={sort('indicatiebeoogdeprockomtovereen')}>
                  Indicatiebeoogdeprockomtovereen <FontAwesomeIcon icon={getSortIconByFieldName('indicatiebeoogdeprockomtovereen')} />
                </th>
                <th className="hand" onClick={sort('indicatieeenmaligelos')}>
                  Indicatieeenmaligelos <FontAwesomeIcon icon={getSortIconByFieldName('indicatieeenmaligelos')} />
                </th>
                <th className="hand" onClick={sort('indicatiemeerjarigeraamovereenkomst')}>
                  Indicatiemeerjarigeraamovereenkomst{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('indicatiemeerjarigeraamovereenkomst')} />
                </th>
                <th className="hand" onClick={sort('indicatiemeerjarigrepeterend')}>
                  Indicatiemeerjarigrepeterend <FontAwesomeIcon icon={getSortIconByFieldName('indicatiemeerjarigrepeterend')} />
                </th>
                <th className="hand" onClick={sort('indicatoroverkoepelendproject')}>
                  Indicatoroverkoepelendproject <FontAwesomeIcon icon={getSortIconByFieldName('indicatoroverkoepelendproject')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('opdrachtcategorie')}>
                  Opdrachtcategorie <FontAwesomeIcon icon={getSortIconByFieldName('opdrachtcategorie')} />
                </th>
                <th className="hand" onClick={sort('opdrachtsoort')}>
                  Opdrachtsoort <FontAwesomeIcon icon={getSortIconByFieldName('opdrachtsoort')} />
                </th>
                <th className="hand" onClick={sort('toelichtingaanvullendeopdracht')}>
                  Toelichtingaanvullendeopdracht <FontAwesomeIcon icon={getSortIconByFieldName('toelichtingaanvullendeopdracht')} />
                </th>
                <th className="hand" onClick={sort('toelichtingeenmaligofrepeterend')}>
                  Toelichtingeenmaligofrepeterend <FontAwesomeIcon icon={getSortIconByFieldName('toelichtingeenmaligofrepeterend')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {startformulieraanbestedenList.map((startformulieraanbesteden, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/startformulieraanbesteden/${startformulieraanbesteden.id}`} color="link" size="sm">
                      {startformulieraanbesteden.id}
                    </Button>
                  </td>
                  <td>{startformulieraanbesteden.beoogdelooptijd}</td>
                  <td>{startformulieraanbesteden.beoogdetotaleopdrachtwaarde}</td>
                  <td>{startformulieraanbesteden.indicatieaanvullendeopdrachtleverancier ? 'true' : 'false'}</td>
                  <td>{startformulieraanbesteden.indicatiebeoogdeaanbestedingonderhands}</td>
                  <td>{startformulieraanbesteden.indicatiebeoogdeprockomtovereen ? 'true' : 'false'}</td>
                  <td>{startformulieraanbesteden.indicatieeenmaligelos}</td>
                  <td>{startformulieraanbesteden.indicatiemeerjarigeraamovereenkomst ? 'true' : 'false'}</td>
                  <td>{startformulieraanbesteden.indicatiemeerjarigrepeterend}</td>
                  <td>{startformulieraanbesteden.indicatoroverkoepelendproject}</td>
                  <td>{startformulieraanbesteden.omschrijving}</td>
                  <td>{startformulieraanbesteden.opdrachtcategorie}</td>
                  <td>{startformulieraanbesteden.opdrachtsoort}</td>
                  <td>{startformulieraanbesteden.toelichtingaanvullendeopdracht}</td>
                  <td>{startformulieraanbesteden.toelichtingeenmaligofrepeterend}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/startformulieraanbesteden/${startformulieraanbesteden.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/startformulieraanbesteden/${startformulieraanbesteden.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/startformulieraanbesteden/${startformulieraanbesteden.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Startformulieraanbestedens found</div>
        )}
      </div>
    </div>
  );
};

export default Startformulieraanbesteden;
