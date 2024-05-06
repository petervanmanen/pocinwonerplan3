import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Disciplinairemaatregel from './disciplinairemaatregel';
import DisciplinairemaatregelDetail from './disciplinairemaatregel-detail';
import DisciplinairemaatregelUpdate from './disciplinairemaatregel-update';
import DisciplinairemaatregelDeleteDialog from './disciplinairemaatregel-delete-dialog';

const DisciplinairemaatregelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Disciplinairemaatregel />} />
    <Route path="new" element={<DisciplinairemaatregelUpdate />} />
    <Route path=":id">
      <Route index element={<DisciplinairemaatregelDetail />} />
      <Route path="edit" element={<DisciplinairemaatregelUpdate />} />
      <Route path="delete" element={<DisciplinairemaatregelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DisciplinairemaatregelRoutes;
