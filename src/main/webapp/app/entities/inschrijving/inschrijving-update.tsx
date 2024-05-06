import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISchool } from 'app/shared/model/school.model';
import { getEntities as getSchools } from 'app/entities/school/school.reducer';
import { IAanbesteding } from 'app/shared/model/aanbesteding.model';
import { getEntities as getAanbestedings } from 'app/entities/aanbesteding/aanbesteding.reducer';
import { ILeerling } from 'app/shared/model/leerling.model';
import { getEntities as getLeerlings } from 'app/entities/leerling/leerling.reducer';
import { ILeverancier } from 'app/shared/model/leverancier.model';
import { getEntities as getLeveranciers } from 'app/entities/leverancier/leverancier.reducer';
import { IInschrijving } from 'app/shared/model/inschrijving.model';
import { getEntity, updateEntity, createEntity, reset } from './inschrijving.reducer';

export const InschrijvingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const schools = useAppSelector(state => state.school.entities);
  const aanbestedings = useAppSelector(state => state.aanbesteding.entities);
  const leerlings = useAppSelector(state => state.leerling.entities);
  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const inschrijvingEntity = useAppSelector(state => state.inschrijving.entity);
  const loading = useAppSelector(state => state.inschrijving.loading);
  const updating = useAppSelector(state => state.inschrijving.updating);
  const updateSuccess = useAppSelector(state => state.inschrijving.updateSuccess);

  const handleClose = () => {
    navigate('/inschrijving');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSchools({}));
    dispatch(getAanbestedings({}));
    dispatch(getLeerlings({}));
    dispatch(getLeveranciers({}));
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
      ...inschrijvingEntity,
      ...values,
      heeftSchool: schools.find(it => it.id.toString() === values.heeftSchool?.toString()),
      betreftAanbesteding: aanbestedings.find(it => it.id.toString() === values.betreftAanbesteding?.toString()),
      heeftLeerling: leerlings.find(it => it.id.toString() === values.heeftLeerling?.toString()),
      heeftLeverancier: leveranciers.find(it => it.id.toString() === values.heeftLeverancier?.toString()),
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
          ...inschrijvingEntity,
          heeftSchool: inschrijvingEntity?.heeftSchool?.id,
          betreftAanbesteding: inschrijvingEntity?.betreftAanbesteding?.id,
          heeftLeerling: inschrijvingEntity?.heeftLeerling?.id,
          heeftLeverancier: inschrijvingEntity?.heeftLeverancier?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.inschrijving.home.createOrEditLabel" data-cy="InschrijvingCreateUpdateHeading">
            Create or edit a Inschrijving
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="inschrijving-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datum" id="inschrijving-datum" name="datum" data-cy="datum" type="date" />
              <ValidatedField id="inschrijving-heeftSchool" name="heeftSchool" data-cy="heeftSchool" label="Heeft School" type="select">
                <option value="" key="0" />
                {schools
                  ? schools.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="inschrijving-betreftAanbesteding"
                name="betreftAanbesteding"
                data-cy="betreftAanbesteding"
                label="Betreft Aanbesteding"
                type="select"
              >
                <option value="" key="0" />
                {aanbestedings
                  ? aanbestedings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="inschrijving-heeftLeerling"
                name="heeftLeerling"
                data-cy="heeftLeerling"
                label="Heeft Leerling"
                type="select"
              >
                <option value="" key="0" />
                {leerlings
                  ? leerlings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="inschrijving-heeftLeverancier"
                name="heeftLeverancier"
                data-cy="heeftLeverancier"
                label="Heeft Leverancier"
                type="select"
              >
                <option value="" key="0" />
                {leveranciers
                  ? leveranciers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inschrijving" replace color="info">
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

export default InschrijvingUpdate;
