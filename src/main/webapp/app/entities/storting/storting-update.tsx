import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMilieustraat } from 'app/shared/model/milieustraat.model';
import { getEntities as getMilieustraats } from 'app/entities/milieustraat/milieustraat.reducer';
import { IFractie } from 'app/shared/model/fractie.model';
import { getEntities as getFracties } from 'app/entities/fractie/fractie.reducer';
import { IPas } from 'app/shared/model/pas.model';
import { getEntities as getPas } from 'app/entities/pas/pas.reducer';
import { IStorting } from 'app/shared/model/storting.model';
import { getEntity, updateEntity, createEntity, reset } from './storting.reducer';

export const StortingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const milieustraats = useAppSelector(state => state.milieustraat.entities);
  const fracties = useAppSelector(state => state.fractie.entities);
  const pas = useAppSelector(state => state.pas.entities);
  const stortingEntity = useAppSelector(state => state.storting.entity);
  const loading = useAppSelector(state => state.storting.loading);
  const updating = useAppSelector(state => state.storting.updating);
  const updateSuccess = useAppSelector(state => state.storting.updateSuccess);

  const handleClose = () => {
    navigate('/storting');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMilieustraats({}));
    dispatch(getFracties({}));
    dispatch(getPas({}));
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
      ...stortingEntity,
      ...values,
      bijMilieustraat: milieustraats.find(it => it.id.toString() === values.bijMilieustraat?.toString()),
      fractieFracties: mapIdList(values.fractieFracties),
      uitgevoerdestortingPas: pas.find(it => it.id.toString() === values.uitgevoerdestortingPas?.toString()),
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
          ...stortingEntity,
          bijMilieustraat: stortingEntity?.bijMilieustraat?.id,
          fractieFracties: stortingEntity?.fractieFracties?.map(e => e.id.toString()),
          uitgevoerdestortingPas: stortingEntity?.uitgevoerdestortingPas?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.storting.home.createOrEditLabel" data-cy="StortingCreateUpdateHeading">
            Create or edit a Storting
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="storting-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datumtijd" id="storting-datumtijd" name="datumtijd" data-cy="datumtijd" type="text" />
              <ValidatedField label="Gewicht" id="storting-gewicht" name="gewicht" data-cy="gewicht" type="text" />
              <ValidatedField
                id="storting-bijMilieustraat"
                name="bijMilieustraat"
                data-cy="bijMilieustraat"
                label="Bij Milieustraat"
                type="select"
              >
                <option value="" key="0" />
                {milieustraats
                  ? milieustraats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Fractie Fractie"
                id="storting-fractieFractie"
                data-cy="fractieFractie"
                type="select"
                multiple
                name="fractieFracties"
              >
                <option value="" key="0" />
                {fracties
                  ? fracties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="storting-uitgevoerdestortingPas"
                name="uitgevoerdestortingPas"
                data-cy="uitgevoerdestortingPas"
                label="Uitgevoerdestorting Pas"
                type="select"
              >
                <option value="" key="0" />
                {pas
                  ? pas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/storting" replace color="info">
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

export default StortingUpdate;
