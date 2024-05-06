import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBuurt } from 'app/shared/model/buurt.model';
import { getEntities as getBuurts } from 'app/entities/buurt/buurt.reducer';
import { IWijk } from 'app/shared/model/wijk.model';
import { getEntities as getWijks } from 'app/entities/wijk/wijk.reducer';
import { ISchouwronde } from 'app/shared/model/schouwronde.model';
import { getEntities as getSchouwrondes } from 'app/entities/schouwronde/schouwronde.reducer';
import { IAreaal } from 'app/shared/model/areaal.model';
import { getEntity, updateEntity, createEntity, reset } from './areaal.reducer';

export const AreaalUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const buurts = useAppSelector(state => state.buurt.entities);
  const wijks = useAppSelector(state => state.wijk.entities);
  const schouwrondes = useAppSelector(state => state.schouwronde.entities);
  const areaalEntity = useAppSelector(state => state.areaal.entity);
  const loading = useAppSelector(state => state.areaal.loading);
  const updating = useAppSelector(state => state.areaal.updating);
  const updateSuccess = useAppSelector(state => state.areaal.updateSuccess);

  const handleClose = () => {
    navigate('/areaal');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBuurts({}));
    dispatch(getWijks({}));
    dispatch(getSchouwrondes({}));
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
      ...areaalEntity,
      ...values,
      ligtinBuurts: mapIdList(values.ligtinBuurts),
      valtbinnenWijks: mapIdList(values.valtbinnenWijks),
      binnenSchouwrondes: mapIdList(values.binnenSchouwrondes),
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
          ...areaalEntity,
          ligtinBuurts: areaalEntity?.ligtinBuurts?.map(e => e.id.toString()),
          valtbinnenWijks: areaalEntity?.valtbinnenWijks?.map(e => e.id.toString()),
          binnenSchouwrondes: areaalEntity?.binnenSchouwrondes?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.areaal.home.createOrEditLabel" data-cy="AreaalCreateUpdateHeading">
            Create or edit a Areaal
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="areaal-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Geometrie" id="areaal-geometrie" name="geometrie" data-cy="geometrie" type="text" />
              <ValidatedField label="Ligtin Buurt" id="areaal-ligtinBuurt" data-cy="ligtinBuurt" type="select" multiple name="ligtinBuurts">
                <option value="" key="0" />
                {buurts
                  ? buurts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Valtbinnen Wijk"
                id="areaal-valtbinnenWijk"
                data-cy="valtbinnenWijk"
                type="select"
                multiple
                name="valtbinnenWijks"
              >
                <option value="" key="0" />
                {wijks
                  ? wijks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Binnen Schouwronde"
                id="areaal-binnenSchouwronde"
                data-cy="binnenSchouwronde"
                type="select"
                multiple
                name="binnenSchouwrondes"
              >
                <option value="" key="0" />
                {schouwrondes
                  ? schouwrondes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/areaal" replace color="info">
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

export default AreaalUpdate;
