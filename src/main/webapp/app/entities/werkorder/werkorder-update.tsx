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
import { IWerkorder } from 'app/shared/model/werkorder.model';
import { getEntity, updateEntity, createEntity, reset } from './werkorder.reducer';

export const WerkorderUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const hoofdrekenings = useAppSelector(state => state.hoofdrekening.entities);
  const kostenplaats = useAppSelector(state => state.kostenplaats.entities);
  const werkorderEntity = useAppSelector(state => state.werkorder.entity);
  const loading = useAppSelector(state => state.werkorder.loading);
  const updating = useAppSelector(state => state.werkorder.updating);
  const updateSuccess = useAppSelector(state => state.werkorder.updateSuccess);

  const handleClose = () => {
    navigate('/werkorder');
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
      ...werkorderEntity,
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
          ...werkorderEntity,
          heeftHoofdrekening: werkorderEntity?.heeftHoofdrekening?.id,
          heeftKostenplaats: werkorderEntity?.heeftKostenplaats?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.werkorder.home.createOrEditLabel" data-cy="WerkorderCreateUpdateHeading">
            Create or edit a Werkorder
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="werkorder-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Code"
                id="werkorder-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Documentnummer"
                id="werkorder-documentnummer"
                name="documentnummer"
                data-cy="documentnummer"
                type="text"
              />
              <ValidatedField label="Naam" id="werkorder-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="werkorder-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField label="Werkordertype" id="werkorder-werkordertype" name="werkordertype" data-cy="werkordertype" type="text" />
              <ValidatedField
                id="werkorder-heeftHoofdrekening"
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
                id="werkorder-heeftKostenplaats"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/werkorder" replace color="info">
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

export default WerkorderUpdate;
