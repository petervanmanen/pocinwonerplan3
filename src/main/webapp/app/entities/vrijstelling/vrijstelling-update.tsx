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
import { IVrijstelling } from 'app/shared/model/vrijstelling.model';
import { getEntity, updateEntity, createEntity, reset } from './vrijstelling.reducer';

export const VrijstellingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const schools = useAppSelector(state => state.school.entities);
  const leerlings = useAppSelector(state => state.leerling.entities);
  const vrijstellingEntity = useAppSelector(state => state.vrijstelling.entity);
  const loading = useAppSelector(state => state.vrijstelling.loading);
  const updating = useAppSelector(state => state.vrijstelling.updating);
  const updateSuccess = useAppSelector(state => state.vrijstelling.updateSuccess);

  const handleClose = () => {
    navigate('/vrijstelling');
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
      ...vrijstellingEntity,
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
          ...vrijstellingEntity,
          heeftSchool: vrijstellingEntity?.heeftSchool?.id,
          heeftLeerling: vrijstellingEntity?.heeftLeerling?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vrijstelling.home.createOrEditLabel" data-cy="VrijstellingCreateUpdateHeading">
            Create or edit a Vrijstelling
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="vrijstelling-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aanvraagtoegekend"
                id="vrijstelling-aanvraagtoegekend"
                name="aanvraagtoegekend"
                data-cy="aanvraagtoegekend"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Buitenlandseschoollocatie"
                id="vrijstelling-buitenlandseschoollocatie"
                name="buitenlandseschoollocatie"
                data-cy="buitenlandseschoollocatie"
                type="text"
              />
              <ValidatedField label="Datumeinde" id="vrijstelling-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="vrijstelling-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField label="Verzuimsoort" id="vrijstelling-verzuimsoort" name="verzuimsoort" data-cy="verzuimsoort" type="text" />
              <ValidatedField id="vrijstelling-heeftSchool" name="heeftSchool" data-cy="heeftSchool" label="Heeft School" type="select">
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
                id="vrijstelling-heeftLeerling"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vrijstelling" replace color="info">
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

export default VrijstellingUpdate;
