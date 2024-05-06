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
import { ILeerling } from 'app/shared/model/leerling.model';
import { getEntities as getLeerlings } from 'app/entities/leerling/leerling.reducer';
import { IVerzuimmelding } from 'app/shared/model/verzuimmelding.model';
import { getEntity, updateEntity, createEntity, reset } from './verzuimmelding.reducer';

export const VerzuimmeldingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const schools = useAppSelector(state => state.school.entities);
  const leerlings = useAppSelector(state => state.leerling.entities);
  const verzuimmeldingEntity = useAppSelector(state => state.verzuimmelding.entity);
  const loading = useAppSelector(state => state.verzuimmelding.loading);
  const updating = useAppSelector(state => state.verzuimmelding.updating);
  const updateSuccess = useAppSelector(state => state.verzuimmelding.updateSuccess);

  const handleClose = () => {
    navigate('/verzuimmelding');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSchools({}));
    dispatch(getLeerlings({}));
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
      ...verzuimmeldingEntity,
      ...values,
      heeftSchool: schools.find(it => it.id.toString() === values.heeftSchool?.toString()),
      heeftLeerling: leerlings.find(it => it.id.toString() === values.heeftLeerling?.toString()),
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
          ...verzuimmeldingEntity,
          heeftSchool: verzuimmeldingEntity?.heeftSchool?.id,
          heeftLeerling: verzuimmeldingEntity?.heeftLeerling?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.verzuimmelding.home.createOrEditLabel" data-cy="VerzuimmeldingCreateUpdateHeading">
            Create or edit a Verzuimmelding
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="verzuimmelding-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datumeinde" id="verzuimmelding-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="verzuimmelding-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField
                label="Voorstelschool"
                id="verzuimmelding-voorstelschool"
                name="voorstelschool"
                data-cy="voorstelschool"
                type="text"
              />
              <ValidatedField id="verzuimmelding-heeftSchool" name="heeftSchool" data-cy="heeftSchool" label="Heeft School" type="select">
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
                id="verzuimmelding-heeftLeerling"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/verzuimmelding" replace color="info">
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

export default VerzuimmeldingUpdate;
