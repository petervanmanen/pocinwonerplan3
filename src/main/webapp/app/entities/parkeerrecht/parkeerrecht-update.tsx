import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBelprovider } from 'app/shared/model/belprovider.model';
import { getEntities as getBelproviders } from 'app/entities/belprovider/belprovider.reducer';
import { IVoertuig } from 'app/shared/model/voertuig.model';
import { getEntities as getVoertuigs } from 'app/entities/voertuig/voertuig.reducer';
import { IParkeerrecht } from 'app/shared/model/parkeerrecht.model';
import { getEntity, updateEntity, createEntity, reset } from './parkeerrecht.reducer';

export const ParkeerrechtUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const belproviders = useAppSelector(state => state.belprovider.entities);
  const voertuigs = useAppSelector(state => state.voertuig.entities);
  const parkeerrechtEntity = useAppSelector(state => state.parkeerrecht.entity);
  const loading = useAppSelector(state => state.parkeerrecht.loading);
  const updating = useAppSelector(state => state.parkeerrecht.updating);
  const updateSuccess = useAppSelector(state => state.parkeerrecht.updateSuccess);

  const handleClose = () => {
    navigate('/parkeerrecht');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBelproviders({}));
    dispatch(getVoertuigs({}));
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
    if (values.bedragaankoop !== undefined && typeof values.bedragaankoop !== 'number') {
      values.bedragaankoop = Number(values.bedragaankoop);
    }
    if (values.bedragbtw !== undefined && typeof values.bedragbtw !== 'number') {
      values.bedragbtw = Number(values.bedragbtw);
    }

    const entity = {
      ...parkeerrechtEntity,
      ...values,
      leverancierBelprovider: belproviders.find(it => it.id.toString() === values.leverancierBelprovider?.toString()),
      betreftVoertuig: voertuigs.find(it => it.id.toString() === values.betreftVoertuig?.toString()),
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
          ...parkeerrechtEntity,
          leverancierBelprovider: parkeerrechtEntity?.leverancierBelprovider?.id,
          betreftVoertuig: parkeerrechtEntity?.betreftVoertuig?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.parkeerrecht.home.createOrEditLabel" data-cy="ParkeerrechtCreateUpdateHeading">
            Create or edit a Parkeerrecht
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="parkeerrecht-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Aanmaaktijd" id="parkeerrecht-aanmaaktijd" name="aanmaaktijd" data-cy="aanmaaktijd" type="text" />
              <ValidatedField
                label="Bedragaankoop"
                id="parkeerrecht-bedragaankoop"
                name="bedragaankoop"
                data-cy="bedragaankoop"
                type="text"
              />
              <ValidatedField label="Bedragbtw" id="parkeerrecht-bedragbtw" name="bedragbtw" data-cy="bedragbtw" type="text" />
              <ValidatedField
                label="Datumtijdeinde"
                id="parkeerrecht-datumtijdeinde"
                name="datumtijdeinde"
                data-cy="datumtijdeinde"
                type="text"
              />
              <ValidatedField
                label="Datumtijdstart"
                id="parkeerrecht-datumtijdstart"
                name="datumtijdstart"
                data-cy="datumtijdstart"
                type="text"
              />
              <ValidatedField label="Productnaam" id="parkeerrecht-productnaam" name="productnaam" data-cy="productnaam" type="text" />
              <ValidatedField
                label="Productomschrijving"
                id="parkeerrecht-productomschrijving"
                name="productomschrijving"
                data-cy="productomschrijving"
                type="text"
              />
              <ValidatedField
                id="parkeerrecht-leverancierBelprovider"
                name="leverancierBelprovider"
                data-cy="leverancierBelprovider"
                label="Leverancier Belprovider"
                type="select"
              >
                <option value="" key="0" />
                {belproviders
                  ? belproviders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="parkeerrecht-betreftVoertuig"
                name="betreftVoertuig"
                data-cy="betreftVoertuig"
                label="Betreft Voertuig"
                type="select"
              >
                <option value="" key="0" />
                {voertuigs
                  ? voertuigs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/parkeerrecht" replace color="info">
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

export default ParkeerrechtUpdate;
