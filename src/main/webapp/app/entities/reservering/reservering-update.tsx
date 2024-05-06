import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVoorziening } from 'app/shared/model/voorziening.model';
import { getEntities as getVoorzienings } from 'app/entities/voorziening/voorziening.reducer';
import { IZaal } from 'app/shared/model/zaal.model';
import { getEntities as getZaals } from 'app/entities/zaal/zaal.reducer';
import { IActiviteit } from 'app/shared/model/activiteit.model';
import { getEntities as getActiviteits } from 'app/entities/activiteit/activiteit.reducer';
import { IReservering } from 'app/shared/model/reservering.model';
import { getEntity, updateEntity, createEntity, reset } from './reservering.reducer';

export const ReserveringUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const voorzienings = useAppSelector(state => state.voorziening.entities);
  const zaals = useAppSelector(state => state.zaal.entities);
  const activiteits = useAppSelector(state => state.activiteit.entities);
  const reserveringEntity = useAppSelector(state => state.reservering.entity);
  const loading = useAppSelector(state => state.reservering.loading);
  const updating = useAppSelector(state => state.reservering.updating);
  const updateSuccess = useAppSelector(state => state.reservering.updateSuccess);

  const handleClose = () => {
    navigate('/reservering');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVoorzienings({}));
    dispatch(getZaals({}));
    dispatch(getActiviteits({}));
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
      ...reserveringEntity,
      ...values,
      betreftVoorziening: voorzienings.find(it => it.id.toString() === values.betreftVoorziening?.toString()),
      betreftZaal: zaals.find(it => it.id.toString() === values.betreftZaal?.toString()),
      heeftActiviteit: activiteits.find(it => it.id.toString() === values.heeftActiviteit?.toString()),
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
          ...reserveringEntity,
          betreftVoorziening: reserveringEntity?.betreftVoorziening?.id,
          betreftZaal: reserveringEntity?.betreftZaal?.id,
          heeftActiviteit: reserveringEntity?.heeftActiviteit?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.reservering.home.createOrEditLabel" data-cy="ReserveringCreateUpdateHeading">
            Create or edit a Reservering
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="reservering-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Aantal" id="reservering-aantal" name="aantal" data-cy="aantal" type="text" />
              <ValidatedField
                label="Btw"
                id="reservering-btw"
                name="btw"
                data-cy="btw"
                type="text"
                validate={{
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField label="Tijdtot" id="reservering-tijdtot" name="tijdtot" data-cy="tijdtot" type="text" />
              <ValidatedField label="Tijdvanaf" id="reservering-tijdvanaf" name="tijdvanaf" data-cy="tijdvanaf" type="text" />
              <ValidatedField label="Totaalprijs" id="reservering-totaalprijs" name="totaalprijs" data-cy="totaalprijs" type="text" />
              <ValidatedField
                id="reservering-betreftVoorziening"
                name="betreftVoorziening"
                data-cy="betreftVoorziening"
                label="Betreft Voorziening"
                type="select"
              >
                <option value="" key="0" />
                {voorzienings
                  ? voorzienings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="reservering-betreftZaal" name="betreftZaal" data-cy="betreftZaal" label="Betreft Zaal" type="select">
                <option value="" key="0" />
                {zaals
                  ? zaals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="reservering-heeftActiviteit"
                name="heeftActiviteit"
                data-cy="heeftActiviteit"
                label="Heeft Activiteit"
                type="select"
              >
                <option value="" key="0" />
                {activiteits
                  ? activiteits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/reservering" replace color="info">
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

export default ReserveringUpdate;
