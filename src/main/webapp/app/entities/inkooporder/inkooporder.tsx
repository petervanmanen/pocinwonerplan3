import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './inkooporder.reducer';

export const Inkooporder = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const inkooporderList = useAppSelector(state => state.inkooporder.entities);
  const loading = useAppSelector(state => state.inkooporder.loading);

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
      <h2 id="inkooporder-heading" data-cy="InkooporderHeading">
        Inkooporders
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/inkooporder/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Inkooporder
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {inkooporderList && inkooporderList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('artikelcode')}>
                  Artikelcode <FontAwesomeIcon icon={getSortIconByFieldName('artikelcode')} />
                </th>
                <th className="hand" onClick={sort('betalingmeerderejaren')}>
                  Betalingmeerderejaren <FontAwesomeIcon icon={getSortIconByFieldName('betalingmeerderejaren')} />
                </th>
                <th className="hand" onClick={sort('betreft')}>
                  Betreft <FontAwesomeIcon icon={getSortIconByFieldName('betreft')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumingediend')}>
                  Datumingediend <FontAwesomeIcon icon={getSortIconByFieldName('datumingediend')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('goederencode')}>
                  Goederencode <FontAwesomeIcon icon={getSortIconByFieldName('goederencode')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('ordernummer')}>
                  Ordernummer <FontAwesomeIcon icon={getSortIconByFieldName('ordernummer')} />
                </th>
                <th className="hand" onClick={sort('saldo')}>
                  Saldo <FontAwesomeIcon icon={getSortIconByFieldName('saldo')} />
                </th>
                <th className="hand" onClick={sort('totaalnettobedrag')}>
                  Totaalnettobedrag <FontAwesomeIcon icon={getSortIconByFieldName('totaalnettobedrag')} />
                </th>
                <th className="hand" onClick={sort('wijzevanaanbesteden')}>
                  Wijzevanaanbesteden <FontAwesomeIcon icon={getSortIconByFieldName('wijzevanaanbesteden')} />
                </th>
                <th>
                  Betreft Contract <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Oorspronkelijk Inkooporder <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Inkooppakket <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Verplichtingaan Leverancier <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Wordtgeschrevenop Hoofdrekening <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Gerelateerd Inkooporder 2 <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Kostenplaats <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {inkooporderList.map((inkooporder, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/inkooporder/${inkooporder.id}`} color="link" size="sm">
                      {inkooporder.id}
                    </Button>
                  </td>
                  <td>{inkooporder.artikelcode}</td>
                  <td>{inkooporder.betalingmeerderejaren ? 'true' : 'false'}</td>
                  <td>{inkooporder.betreft}</td>
                  <td>{inkooporder.datumeinde}</td>
                  <td>{inkooporder.datumingediend}</td>
                  <td>{inkooporder.datumstart}</td>
                  <td>{inkooporder.goederencode}</td>
                  <td>{inkooporder.omschrijving}</td>
                  <td>{inkooporder.ordernummer}</td>
                  <td>{inkooporder.saldo}</td>
                  <td>{inkooporder.totaalnettobedrag}</td>
                  <td>{inkooporder.wijzevanaanbesteden}</td>
                  <td>
                    {inkooporder.betreftContract ? (
                      <Link to={`/contract/${inkooporder.betreftContract.id}`}>{inkooporder.betreftContract.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inkooporder.oorspronkelijkInkooporder ? (
                      <Link to={`/inkooporder/${inkooporder.oorspronkelijkInkooporder.id}`}>
                        {inkooporder.oorspronkelijkInkooporder.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inkooporder.heeftInkooppakket ? (
                      <Link to={`/inkooppakket/${inkooporder.heeftInkooppakket.id}`}>{inkooporder.heeftInkooppakket.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inkooporder.verplichtingaanLeverancier ? (
                      <Link to={`/leverancier/${inkooporder.verplichtingaanLeverancier.id}`}>
                        {inkooporder.verplichtingaanLeverancier.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inkooporder.wordtgeschrevenopHoofdrekenings
                      ? inkooporder.wordtgeschrevenopHoofdrekenings.map((val, j) => (
                          <span key={j}>
                            <Link to={`/hoofdrekening/${val.id}`}>{val.id}</Link>
                            {j === inkooporder.wordtgeschrevenopHoofdrekenings.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {inkooporder.gerelateerdInkooporder2 ? (
                      <Link to={`/inkooporder/${inkooporder.gerelateerdInkooporder2.id}`}>{inkooporder.gerelateerdInkooporder2.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inkooporder.heeftKostenplaats
                      ? inkooporder.heeftKostenplaats.map((val, j) => (
                          <span key={j}>
                            <Link to={`/kostenplaats/${val.id}`}>{val.id}</Link>
                            {j === inkooporder.heeftKostenplaats.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/inkooporder/${inkooporder.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/inkooporder/${inkooporder.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/inkooporder/${inkooporder.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Inkooporders found</div>
        )}
      </div>
    </div>
  );
};

export default Inkooporder;
