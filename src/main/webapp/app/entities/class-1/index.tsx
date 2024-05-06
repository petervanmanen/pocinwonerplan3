import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Class1 from './class-1';
import Class1Detail from './class-1-detail';
import Class1Update from './class-1-update';
import Class1DeleteDialog from './class-1-delete-dialog';

const Class1Routes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Class1 />} />
    <Route path="new" element={<Class1Update />} />
    <Route path=":id">
      <Route index element={<Class1Detail />} />
      <Route path="edit" element={<Class1Update />} />
      <Route path="delete" element={<Class1DeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default Class1Routes;
