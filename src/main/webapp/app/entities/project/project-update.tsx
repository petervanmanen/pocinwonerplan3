import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUitstroomreden } from 'app/shared/model/uitstroomreden.model';
import { getEntities as getUitstroomredens } from 'app/entities/uitstroomreden/uitstroomreden.reducer';
import { IResultaat } from 'app/shared/model/resultaat.model';
import { getEntities as getResultaats } from 'app/entities/resultaat/resultaat.reducer';
import { IProjectsoort } from 'app/shared/model/projectsoort.model';
import { getEntities as getProjectsoorts } from 'app/entities/projectsoort/projectsoort.reducer';
import { ILocatie } from 'app/shared/model/locatie.model';
import { getEntities as getLocaties } from 'app/entities/locatie/locatie.reducer';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { getEntities as getKostenplaats } from 'app/entities/kostenplaats/kostenplaats.reducer';
import { ITraject } from 'app/shared/model/traject.model';
import { getEntities as getTrajects } from 'app/entities/traject/traject.reducer';
import { IProject } from 'app/shared/model/project.model';
import { getEntity, updateEntity, createEntity, reset } from './project.reducer';

export const ProjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const uitstroomredens = useAppSelector(state => state.uitstroomreden.entities);
  const resultaats = useAppSelector(state => state.resultaat.entities);
  const projectsoorts = useAppSelector(state => state.projectsoort.entities);
  const locaties = useAppSelector(state => state.locatie.entities);
  const kostenplaats = useAppSelector(state => state.kostenplaats.entities);
  const trajects = useAppSelector(state => state.traject.entities);
  const projectEntity = useAppSelector(state => state.project.entity);
  const loading = useAppSelector(state => state.project.loading);
  const updating = useAppSelector(state => state.project.updating);
  const updateSuccess = useAppSelector(state => state.project.updateSuccess);

  const handleClose = () => {
    navigate('/project');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUitstroomredens({}));
    dispatch(getResultaats({}));
    dispatch(getProjectsoorts({}));
    dispatch(getLocaties({}));
    dispatch(getKostenplaats({}));
    dispatch(getTrajects({}));
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
      ...projectEntity,
      ...values,
      heeftuitstroomredenUitstroomreden: uitstroomredens.find(
        it => it.id.toString() === values.heeftuitstroomredenUitstroomreden?.toString(),
      ),
      heeftresultaatResultaat: resultaats.find(it => it.id.toString() === values.heeftresultaatResultaat?.toString()),
      soortprojectProjectsoort: projectsoorts.find(it => it.id.toString() === values.soortprojectProjectsoort?.toString()),
      wordtbegrensddoorLocaties: mapIdList(values.wordtbegrensddoorLocaties),
      heeftKostenplaats: mapIdList(values.heeftKostenplaats),
      heeftprojectTraject: trajects.find(it => it.id.toString() === values.heeftprojectTraject?.toString()),
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
          ...projectEntity,
          heeftuitstroomredenUitstroomreden: projectEntity?.heeftuitstroomredenUitstroomreden?.id,
          heeftresultaatResultaat: projectEntity?.heeftresultaatResultaat?.id,
          soortprojectProjectsoort: projectEntity?.soortprojectProjectsoort?.id,
          wordtbegrensddoorLocaties: projectEntity?.wordtbegrensddoorLocaties?.map(e => e.id.toString()),
          heeftKostenplaats: projectEntity?.heeftKostenplaats?.map(e => e.id.toString()),
          heeftprojectTraject: projectEntity?.heeftprojectTraject?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.project.home.createOrEditLabel" data-cy="ProjectCreateUpdateHeading">
            Create or edit a Project
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="project-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Coordinaten" id="project-coordinaten" name="coordinaten" data-cy="coordinaten" type="text" />
              <ValidatedField label="Datumeinde" id="project-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="project-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField label="Jaartot" id="project-jaartot" name="jaartot" data-cy="jaartot" type="text" />
              <ValidatedField label="Jaarvan" id="project-jaarvan" name="jaarvan" data-cy="jaarvan" type="text" />
              <ValidatedField label="Locatie" id="project-locatie" name="locatie" data-cy="locatie" type="text" />
              <ValidatedField label="Naam" id="project-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Naamcode" id="project-naamcode" name="naamcode" data-cy="naamcode" type="text" />
              <ValidatedField label="Projectcd" id="project-projectcd" name="projectcd" data-cy="projectcd" type="text" />
              <ValidatedField label="Toponiem" id="project-toponiem" name="toponiem" data-cy="toponiem" type="text" />
              <ValidatedField label="Trefwoorden" id="project-trefwoorden" name="trefwoorden" data-cy="trefwoorden" type="text" />
              <ValidatedField
                id="project-heeftuitstroomredenUitstroomreden"
                name="heeftuitstroomredenUitstroomreden"
                data-cy="heeftuitstroomredenUitstroomreden"
                label="Heeftuitstroomreden Uitstroomreden"
                type="select"
                required
              >
                <option value="" key="0" />
                {uitstroomredens
                  ? uitstroomredens.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="project-heeftresultaatResultaat"
                name="heeftresultaatResultaat"
                data-cy="heeftresultaatResultaat"
                label="Heeftresultaat Resultaat"
                type="select"
                required
              >
                <option value="" key="0" />
                {resultaats
                  ? resultaats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="project-soortprojectProjectsoort"
                name="soortprojectProjectsoort"
                data-cy="soortprojectProjectsoort"
                label="Soortproject Projectsoort"
                type="select"
              >
                <option value="" key="0" />
                {projectsoorts
                  ? projectsoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Wordtbegrensddoor Locatie"
                id="project-wordtbegrensddoorLocatie"
                data-cy="wordtbegrensddoorLocatie"
                type="select"
                multiple
                name="wordtbegrensddoorLocaties"
              >
                <option value="" key="0" />
                {locaties
                  ? locaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeft Kostenplaats"
                id="project-heeftKostenplaats"
                data-cy="heeftKostenplaats"
                type="select"
                multiple
                name="heeftKostenplaats"
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
              <ValidatedField
                id="project-heeftprojectTraject"
                name="heeftprojectTraject"
                data-cy="heeftprojectTraject"
                label="Heeftproject Traject"
                type="select"
              >
                <option value="" key="0" />
                {trajects
                  ? trajects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/project" replace color="info">
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

export default ProjectUpdate;
