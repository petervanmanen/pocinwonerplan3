import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAreaal } from 'app/shared/model/areaal.model';
import { getEntities as getAreaals } from 'app/entities/areaal/areaal.reducer';
import { IWijk } from 'app/shared/model/wijk.model';
import { getEntity, updateEntity, createEntity, reset } from './wijk.reducer';

export const WijkUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const areaals = useAppSelector(state => state.areaal.entities);
  const wijkEntity = useAppSelector(state => state.wijk.entity);
  const loading = useAppSelector(state => state.wijk.loading);
  const updating = useAppSelector(state => state.wijk.updating);
  const updateSuccess = useAppSelector(state => state.wijk.updateSuccess);

  const handleClose = () => {
    navigate('/wijk');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAreaals({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...wijkEntity,
      ...values,
      valtbinnenAreaals: mapIdList(values.valtbinnenAreaals),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...wijkEntity,
          valtbinnenAreaals: wijkEntity?.valtbinnenAreaals?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.wijk.home.createOrEditLabel" data-cy="WijkCreateUpdateHeading">
            Create or edit a Wijk
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="wijk-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumbegingeldigheid"
                id="wijk-datumbegingeldigheid"
                name="datumbegingeldigheid"
                data-cy="datumbegingeldigheid"
                type="date"
              />
              <ValidatedField label="Datumeinde" id="wijk-datumeinde" name="datumeinde" data-cy="datumeinde" type="text" />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="wijk-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField label="Datumingang" id="wijk-datumingang" name="datumingang" data-cy="datumingang" type="date" />
              <ValidatedField
                label="Geconstateerd"
                id="wijk-geconstateerd"
                name="geconstateerd"
                data-cy="geconstateerd"
                check
                type="checkbox"
              />
              <ValidatedField label="Geometrie" id="wijk-geometrie" name="geometrie" data-cy="geometrie" type="text" />
              <ValidatedField label="Identificatie" id="wijk-identificatie" name="identificatie" data-cy="identificatie" type="text" />
              <ValidatedField label="Inonderzoek" id="wijk-inonderzoek" name="inonderzoek" data-cy="inonderzoek" check type="checkbox" />
              <ValidatedField label="Status" id="wijk-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Versie" id="wijk-versie" name="versie" data-cy="versie" type="text" />
              <ValidatedField label="Wijkcode" id="wijk-wijkcode" name="wijkcode" data-cy="wijkcode" type="text" />
              <ValidatedField label="Wijknaam" id="wijk-wijknaam" name="wijknaam" data-cy="wijknaam" type="text" />
              <ValidatedField
                label="Valtbinnen Areaal"
                id="wijk-valtbinnenAreaal"
                data-cy="valtbinnenAreaal"
                type="select"
                multiple
                name="valtbinnenAreaals"
              >
                <option value="" key="0" />
                {areaals
                  ? areaals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/wijk" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default WijkUpdate;
