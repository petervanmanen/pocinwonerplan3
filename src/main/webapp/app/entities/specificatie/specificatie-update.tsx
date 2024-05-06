import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProjectactiviteit } from 'app/shared/model/projectactiviteit.model';
import { getEntities as getProjectactiviteits } from 'app/entities/projectactiviteit/projectactiviteit.reducer';
import { IVerzoek } from 'app/shared/model/verzoek.model';
import { getEntities as getVerzoeks } from 'app/entities/verzoek/verzoek.reducer';
import { ISpecificatie } from 'app/shared/model/specificatie.model';
import { getEntity, updateEntity, createEntity, reset } from './specificatie.reducer';

export const SpecificatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const projectactiviteits = useAppSelector(state => state.projectactiviteit.entities);
  const verzoeks = useAppSelector(state => state.verzoek.entities);
  const specificatieEntity = useAppSelector(state => state.specificatie.entity);
  const loading = useAppSelector(state => state.specificatie.loading);
  const updating = useAppSelector(state => state.specificatie.updating);
  const updateSuccess = useAppSelector(state => state.specificatie.updateSuccess);

  const handleClose = () => {
    navigate('/specificatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProjectactiviteits({}));
    dispatch(getVerzoeks({}));
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
      ...specificatieEntity,
      ...values,
      gedefinieerddoorProjectactiviteit: projectactiviteits.find(
        it => it.id.toString() === values.gedefinieerddoorProjectactiviteit?.toString(),
      ),
      bevatVerzoek: verzoeks.find(it => it.id.toString() === values.bevatVerzoek?.toString()),
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
          ...specificatieEntity,
          gedefinieerddoorProjectactiviteit: specificatieEntity?.gedefinieerddoorProjectactiviteit?.id,
          bevatVerzoek: specificatieEntity?.bevatVerzoek?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.specificatie.home.createOrEditLabel" data-cy="SpecificatieCreateUpdateHeading">
            Create or edit a Specificatie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="specificatie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Antwoord" id="specificatie-antwoord" name="antwoord" data-cy="antwoord" type="text" />
              <ValidatedField label="Groepering" id="specificatie-groepering" name="groepering" data-cy="groepering" type="text" />
              <ValidatedField
                label="Publiceerbaar"
                id="specificatie-publiceerbaar"
                name="publiceerbaar"
                data-cy="publiceerbaar"
                type="text"
              />
              <ValidatedField
                label="Vraagclassificatie"
                id="specificatie-vraagclassificatie"
                name="vraagclassificatie"
                data-cy="vraagclassificatie"
                type="text"
              />
              <ValidatedField label="Vraagid" id="specificatie-vraagid" name="vraagid" data-cy="vraagid" type="text" />
              <ValidatedField
                label="Vraagreferentie"
                id="specificatie-vraagreferentie"
                name="vraagreferentie"
                data-cy="vraagreferentie"
                type="text"
              />
              <ValidatedField label="Vraagtekst" id="specificatie-vraagtekst" name="vraagtekst" data-cy="vraagtekst" type="text" />
              <ValidatedField
                id="specificatie-gedefinieerddoorProjectactiviteit"
                name="gedefinieerddoorProjectactiviteit"
                data-cy="gedefinieerddoorProjectactiviteit"
                label="Gedefinieerddoor Projectactiviteit"
                type="select"
                required
              >
                <option value="" key="0" />
                {projectactiviteits
                  ? projectactiviteits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField id="specificatie-bevatVerzoek" name="bevatVerzoek" data-cy="bevatVerzoek" label="Bevat Verzoek" type="select">
                <option value="" key="0" />
                {verzoeks
                  ? verzoeks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/specificatie" replace color="info">
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

export default SpecificatieUpdate;
