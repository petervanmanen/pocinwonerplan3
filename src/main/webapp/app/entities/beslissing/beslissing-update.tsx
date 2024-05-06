import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeerling } from 'app/shared/model/leerling.model';
import { getEntities as getLeerlings } from 'app/entities/leerling/leerling.reducer';
import { ILeerplichtambtenaar } from 'app/shared/model/leerplichtambtenaar.model';
import { getEntities as getLeerplichtambtenaars } from 'app/entities/leerplichtambtenaar/leerplichtambtenaar.reducer';
import { ISchool } from 'app/shared/model/school.model';
import { getEntities as getSchools } from 'app/entities/school/school.reducer';
import { IBeslissing } from 'app/shared/model/beslissing.model';
import { getEntity, updateEntity, createEntity, reset } from './beslissing.reducer';

export const BeslissingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leerlings = useAppSelector(state => state.leerling.entities);
  const leerplichtambtenaars = useAppSelector(state => state.leerplichtambtenaar.entities);
  const schools = useAppSelector(state => state.school.entities);
  const beslissingEntity = useAppSelector(state => state.beslissing.entity);
  const loading = useAppSelector(state => state.beslissing.loading);
  const updating = useAppSelector(state => state.beslissing.updating);
  const updateSuccess = useAppSelector(state => state.beslissing.updateSuccess);

  const handleClose = () => {
    navigate('/beslissing');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeerlings({}));
    dispatch(getLeerplichtambtenaars({}));
    dispatch(getSchools({}));
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
      ...beslissingEntity,
      ...values,
      betreftLeerling: leerlings.find(it => it.id.toString() === values.betreftLeerling?.toString()),
      behandelaarLeerplichtambtenaar: leerplichtambtenaars.find(
        it => it.id.toString() === values.behandelaarLeerplichtambtenaar?.toString(),
      ),
      betreftSchool: schools.find(it => it.id.toString() === values.betreftSchool?.toString()),
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
          ...beslissingEntity,
          betreftLeerling: beslissingEntity?.betreftLeerling?.id,
          behandelaarLeerplichtambtenaar: beslissingEntity?.behandelaarLeerplichtambtenaar?.id,
          betreftSchool: beslissingEntity?.betreftSchool?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.beslissing.home.createOrEditLabel" data-cy="BeslissingCreateUpdateHeading">
            Create or edit a Beslissing
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="beslissing-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datum" id="beslissing-datum" name="datum" data-cy="datum" type="date" />
              <ValidatedField label="Opmerkingen" id="beslissing-opmerkingen" name="opmerkingen" data-cy="opmerkingen" type="text" />
              <ValidatedField label="Reden" id="beslissing-reden" name="reden" data-cy="reden" type="text" />
              <ValidatedField
                id="beslissing-betreftLeerling"
                name="betreftLeerling"
                data-cy="betreftLeerling"
                label="Betreft Leerling"
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
                id="beslissing-behandelaarLeerplichtambtenaar"
                name="behandelaarLeerplichtambtenaar"
                data-cy="behandelaarLeerplichtambtenaar"
                label="Behandelaar Leerplichtambtenaar"
                type="select"
              >
                <option value="" key="0" />
                {leerplichtambtenaars
                  ? leerplichtambtenaars.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="beslissing-betreftSchool"
                name="betreftSchool"
                data-cy="betreftSchool"
                label="Betreft School"
                type="select"
              >
                <option value="" key="0" />
                {schools
                  ? schools.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/beslissing" replace color="info">
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

export default BeslissingUpdate;
