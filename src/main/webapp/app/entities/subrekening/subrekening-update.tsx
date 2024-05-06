import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHoofdrekening } from 'app/shared/model/hoofdrekening.model';
import { getEntities as getHoofdrekenings } from 'app/entities/hoofdrekening/hoofdrekening.reducer';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { getEntities as getKostenplaats } from 'app/entities/kostenplaats/kostenplaats.reducer';
import { ISubrekening } from 'app/shared/model/subrekening.model';
import { getEntity, updateEntity, createEntity, reset } from './subrekening.reducer';

export const SubrekeningUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const hoofdrekenings = useAppSelector(state => state.hoofdrekening.entities);
  const kostenplaats = useAppSelector(state => state.kostenplaats.entities);
  const subrekeningEntity = useAppSelector(state => state.subrekening.entity);
  const loading = useAppSelector(state => state.subrekening.loading);
  const updating = useAppSelector(state => state.subrekening.updating);
  const updateSuccess = useAppSelector(state => state.subrekening.updateSuccess);

  const handleClose = () => {
    navigate('/subrekening');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getHoofdrekenings({}));
    dispatch(getKostenplaats({}));
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
      ...subrekeningEntity,
      ...values,
      heeftHoofdrekening: hoofdrekenings.find(it => it.id.toString() === values.heeftHoofdrekening?.toString()),
      heeftKostenplaats: kostenplaats.find(it => it.id.toString() === values.heeftKostenplaats?.toString()),
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
          ...subrekeningEntity,
          heeftHoofdrekening: subrekeningEntity?.heeftHoofdrekening?.id,
          heeftKostenplaats: subrekeningEntity?.heeftKostenplaats?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.subrekening.home.createOrEditLabel" data-cy="SubrekeningCreateUpdateHeading">
            Create or edit a Subrekening
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="subrekening-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Naam" id="subrekening-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Nummer"
                id="subrekening-nummer"
                name="nummer"
                data-cy="nummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Omschrijving" id="subrekening-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                id="subrekening-heeftHoofdrekening"
                name="heeftHoofdrekening"
                data-cy="heeftHoofdrekening"
                label="Heeft Hoofdrekening"
                type="select"
              >
                <option value="" key="0" />
                {hoofdrekenings
                  ? hoofdrekenings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="subrekening-heeftKostenplaats"
                name="heeftKostenplaats"
                data-cy="heeftKostenplaats"
                label="Heeft Kostenplaats"
                type="select"
              >
                <option value="" key="0" />
                {kostenplaats
                  ? kostenplaats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/subrekening" replace color="info">
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

export default SubrekeningUpdate;
