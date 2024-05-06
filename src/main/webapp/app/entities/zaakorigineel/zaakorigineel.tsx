import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './zaakorigineel.reducer';

export const Zaakorigineel = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const zaakorigineelList = useAppSelector(state => state.zaakorigineel.entities);
  const loading = useAppSelector(state => state.zaakorigineel.loading);

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
      <h2 id="zaakorigineel-heading" data-cy="ZaakorigineelHeading">
        Zaakorigineels
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/zaakorigineel/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Zaakorigineel
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {zaakorigineelList && zaakorigineelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('anderzaakobject')}>
                  Anderzaakobject <FontAwesomeIcon icon={getSortIconByFieldName('anderzaakobject')} />
                </th>
                <th className="hand" onClick={sort('archiefnominatie')}>
                  Archiefnominatie <FontAwesomeIcon icon={getSortIconByFieldName('archiefnominatie')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumeindegepland')}>
                  Datumeindegepland <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegepland')} />
                </th>
                <th className="hand" onClick={sort('datumeindeuiterlijkeafdoening')}>
                  Datumeindeuiterlijkeafdoening <FontAwesomeIcon icon={getSortIconByFieldName('datumeindeuiterlijkeafdoening')} />
                </th>
                <th className="hand" onClick={sort('datumlaatstebetaling')}>
                  Datumlaatstebetaling <FontAwesomeIcon icon={getSortIconByFieldName('datumlaatstebetaling')} />
                </th>
                <th className="hand" onClick={sort('datumpublicatie')}>
                  Datumpublicatie <FontAwesomeIcon icon={getSortIconByFieldName('datumpublicatie')} />
                </th>
                <th className="hand" onClick={sort('datumregistratie')}>
                  Datumregistratie <FontAwesomeIcon icon={getSortIconByFieldName('datumregistratie')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('datumvernietigingdossier')}>
                  Datumvernietigingdossier <FontAwesomeIcon icon={getSortIconByFieldName('datumvernietigingdossier')} />
                </th>
                <th className="hand" onClick={sort('indicatiebetaling')}>
                  Indicatiebetaling <FontAwesomeIcon icon={getSortIconByFieldName('indicatiebetaling')} />
                </th>
                <th className="hand" onClick={sort('indicatiedeelzaken')}>
                  Indicatiedeelzaken <FontAwesomeIcon icon={getSortIconByFieldName('indicatiedeelzaken')} />
                </th>
                <th className="hand" onClick={sort('kenmerk')}>
                  Kenmerk <FontAwesomeIcon icon={getSortIconByFieldName('kenmerk')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('omschrijvingresultaat')}>
                  Omschrijvingresultaat <FontAwesomeIcon icon={getSortIconByFieldName('omschrijvingresultaat')} />
                </th>
                <th className="hand" onClick={sort('opschorting')}>
                  Opschorting <FontAwesomeIcon icon={getSortIconByFieldName('opschorting')} />
                </th>
                <th className="hand" onClick={sort('toelichting')}>
                  Toelichting <FontAwesomeIcon icon={getSortIconByFieldName('toelichting')} />
                </th>
                <th className="hand" onClick={sort('toelichtingresultaat')}>
                  Toelichtingresultaat <FontAwesomeIcon icon={getSortIconByFieldName('toelichtingresultaat')} />
                </th>
                <th className="hand" onClick={sort('verlenging')}>
                  Verlenging <FontAwesomeIcon icon={getSortIconByFieldName('verlenging')} />
                </th>
                <th className="hand" onClick={sort('zaakidentificatie')}>
                  Zaakidentificatie <FontAwesomeIcon icon={getSortIconByFieldName('zaakidentificatie')} />
                </th>
                <th className="hand" onClick={sort('zaakniveau')}>
                  Zaakniveau <FontAwesomeIcon icon={getSortIconByFieldName('zaakniveau')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {zaakorigineelList.map((zaakorigineel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/zaakorigineel/${zaakorigineel.id}`} color="link" size="sm">
                      {zaakorigineel.id}
                    </Button>
                  </td>
                  <td>{zaakorigineel.anderzaakobject}</td>
                  <td>{zaakorigineel.archiefnominatie}</td>
                  <td>{zaakorigineel.datumeinde}</td>
                  <td>{zaakorigineel.datumeindegepland}</td>
                  <td>{zaakorigineel.datumeindeuiterlijkeafdoening}</td>
                  <td>{zaakorigineel.datumlaatstebetaling}</td>
                  <td>{zaakorigineel.datumpublicatie}</td>
                  <td>{zaakorigineel.datumregistratie}</td>
                  <td>{zaakorigineel.datumstart}</td>
                  <td>{zaakorigineel.datumvernietigingdossier}</td>
                  <td>{zaakorigineel.indicatiebetaling}</td>
                  <td>{zaakorigineel.indicatiedeelzaken}</td>
                  <td>{zaakorigineel.kenmerk}</td>
                  <td>{zaakorigineel.omschrijving}</td>
                  <td>{zaakorigineel.omschrijvingresultaat}</td>
                  <td>{zaakorigineel.opschorting}</td>
                  <td>{zaakorigineel.toelichting}</td>
                  <td>{zaakorigineel.toelichtingresultaat}</td>
                  <td>{zaakorigineel.verlenging}</td>
                  <td>{zaakorigineel.zaakidentificatie}</td>
                  <td>{zaakorigineel.zaakniveau}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/zaakorigineel/${zaakorigineel.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/zaakorigineel/${zaakorigineel.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/zaakorigineel/${zaakorigineel.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Zaakorigineels found</div>
        )}
      </div>
    </div>
  );
};

export default Zaakorigineel;
