import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Studentenwoningen from './studentenwoningen';
import StudentenwoningenDetail from './studentenwoningen-detail';
import StudentenwoningenUpdate from './studentenwoningen-update';
import StudentenwoningenDeleteDialog from './studentenwoningen-delete-dialog';

const StudentenwoningenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Studentenwoningen />} />
    <Route path="new" element={<StudentenwoningenUpdate />} />
    <Route path=":id">
      <Route index element={<StudentenwoningenDetail />} />
      <Route path="edit" element={<StudentenwoningenUpdate />} />
      <Route path="delete" element={<StudentenwoningenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StudentenwoningenRoutes;
